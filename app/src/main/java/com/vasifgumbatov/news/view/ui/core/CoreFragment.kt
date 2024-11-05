package com.vasifgumbatov.news.view.ui.core

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class CoreFragment<T: ViewBinding>: Fragment() {
    protected var binding: T? = null
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}