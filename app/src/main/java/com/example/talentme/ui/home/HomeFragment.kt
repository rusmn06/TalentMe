package com.example.talentme.ui.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.talentme.R
import com.example.talentme.ViewModelFactory
import com.example.talentme.databinding.FragmentHomeBinding
import com.example.talentme.ui.profile.ProfileActivity
import com.example.talentme.ui.profile.ProfileActivityViewModel
import com.example.talentme.ui.recomendation.MajorRecomendationActivity
import com.example.talentme.ui.test.passion.TestPassionActivity
import com.example.talentme.ui.test.personality.TestPersonalityActivity
import com.example.talentme.ui.test.talent.TestTalentActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.view3.setOnClickListener {
            val intent = Intent(requireActivity(), TestTalentActivity::class.java)
            startActivity(intent)
        }
        viewModel.getSession().observe(requireActivity()) { user ->
            Log.d("ProfileActivity", "User ID: ${user.id}")
            viewModel.getUserById(user.id)
            viewModel.getUserByIdRoom.observe(viewLifecycleOwner){
                if (it != null){
                    binding.textView12.text = getString(R.string.welcomeHome, it.name)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}