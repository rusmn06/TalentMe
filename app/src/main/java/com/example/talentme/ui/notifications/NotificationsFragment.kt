package com.example.talentme.ui.notifications

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.talentme.R
import com.example.talentme.databinding.FragmentNotificationsBinding
import java.util.Locale

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.setLanguageBtn?.setOnClickListener {
            val languages = arrayOf("Indonesia", "English")
            val selectedLanguageIndex = if (getSelectedLanguage() == "id") 0 else 1

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.choose_language))  // Gunakan string resource jika ada
                .setSingleChoiceItems(languages, selectedLanguageIndex) { dialog, which ->
                    when (which) {
                        0 -> setAppLanguage("id")
                        1 -> setAppLanguage("en")
                    }
                    dialog.dismiss()
                }
                .show()
        }
    }
    private fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)

        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("selected_language", languageCode).apply()

        requireActivity().recreate()
    }
    private fun getSelectedLanguage(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("selected_language", "en") ?: "en"
    }
}