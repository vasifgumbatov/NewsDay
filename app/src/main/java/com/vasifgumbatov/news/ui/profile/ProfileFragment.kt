package com.vasifgumbatov.news.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.vasifgumbatov.news.databinding.FragmentProfileBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import com.vasifgumbatov.news.ui.language.LanguageBottomSheetFragment
import com.vasifgumbatov.news.ui.language.LanguageHelper
import com.vasifgumbatov.news.ui.theme.ThemeVM
import com.vasifgumbatov.news.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : CoreFragment<FragmentProfileBinding>() {
    private val themeVM by activityViewModels<ThemeVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.passToAboutUs?.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(
                MainFragmentDirections.mainToAboutUs()
            )
        }

        binding?.passToCommunity?.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(
                MainFragmentDirections.mainToCommunity()
            )
        }

        setFragmentResultListener("language") { key, bundle ->
            val language = bundle.getString("language")
            when(language){
                "ru" ->{
                    changeLang("ru")
                }
                "es" ->{
                    changeLang("es")
                }
                "de" ->{
                    changeLang("de")
                }
                else ->{
                    changeLang("en")
                }
            }
        }

        binding?.langChangeBTN?.setOnClickListener {
            val languageBottomSheetFragment = LanguageBottomSheetFragment()
            languageBottomSheetFragment.show(
                parentFragmentManager,
                languageBottomSheetFragment.tag
            )
        }

        // Set the current theme
        binding?.modeChangeBtn?.setOnClickListener {
            themeVM.changeTheme()
        }
    }

    private fun changeLang(lang: String) {
        val newContext = LanguageHelper.setLocale(requireContext(), lang)
        requireActivity().recreate()
    }
}