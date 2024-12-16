package com.vasifgumbatov.news.view.ui.profile.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.databinding.FragmentLanguageBottomSheetBinding
import com.vasifgumbatov.news.view.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LanguageBottomSheetFragment @Inject constructor() : BottomSheetDialogFragment() {
    private var binding: FragmentLanguageBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.defaultTV?.setOnClickListener {
            setFragmentResult("language", bundleOf("language" to "en"))
            dismiss()
        }

        binding?.russianTV?.setOnClickListener {
            setFragmentResult("language", bundleOf("language" to "ru"))
            dismiss()
        }

        binding?.spanishTV?.setOnClickListener {
            setFragmentResult("language", bundleOf("language" to "es"))
            dismiss()
        }

        binding?.germanTV?.setOnClickListener {
            setFragmentResult("language", bundleOf("language" to "de"))
            dismiss()
        }

    }
}