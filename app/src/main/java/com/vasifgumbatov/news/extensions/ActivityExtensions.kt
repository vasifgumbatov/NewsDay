package com.vasifgumbatov.news.extensions

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat

fun Activity.setStatusBarColors(colorResId: Int, isIconsOnlyLight: Boolean = false) {
    val window = this.window

    // Set flags for drawing background and handling light/dark icons
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
    if (isIconsOnlyLight){
        window.decorView.systemUiVisibility = 0
        return
    }
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
        true -> {
            window.decorView.systemUiVisibility = 0
        }
        false -> {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}