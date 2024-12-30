package com.vasifgumbatov.news.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.navigateFromParent(@IdRes resId: Int, args: Bundle? = null, navOptions: NavOptions? = null){
    parentFragment?.parentFragment?.findNavController()?.navigate(resId, args, navOptions)
}