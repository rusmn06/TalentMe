package com.example.talentme.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.talentme.databinding.FragmentHomeBinding
import com.example.talentme.ui.profile.ProfileActivity
import com.example.talentme.ui.recomendation.MajorRecomendationActivity
import com.example.talentme.ui.test.passion.TestPassionActivity
import com.example.talentme.ui.test.personality.TestPersonalityActivity
import com.example.talentme.ui.test.talent.TestTalentActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menambahkan OnClickListener pada Button atau elemen UI lainnya
        binding.ivProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.view3.setOnClickListener {
            val intent = Intent(requireActivity(), TestTalentActivity::class.java)
            startActivity(intent)
        }
        binding.view5.setOnClickListener {
            val intent = Intent(requireActivity(), MajorRecomendationActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}