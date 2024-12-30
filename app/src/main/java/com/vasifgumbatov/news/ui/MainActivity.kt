package com.vasifgumbatov.news.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.databinding.ActivityMainBinding
import com.vasifgumbatov.news.extensions.setStatusBarColors
import com.vasifgumbatov.news.ui.language.LanguageHelper
import com.vasifgumbatov.news.ui.theme.ThemeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val themeVM by viewModels<ThemeVM>()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // statusBar color
        setStatusBarColors(R.color.background)

        LanguageHelper.applyLocale(this)

        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)

        themeVM.theme.observe(this) { isDarkMode ->
            setTheme(isDarkMode)
            setDarkMode(isDarkMode)
        }
    }

    override fun onResume() {
        super.onResume()
        checkTheme()
    }

    private fun checkTheme() {
        when (isDark()) {
            true -> themeVM.setDarkMode(true)
            false -> themeVM.setDarkMode(false)
            else -> setTheme(getSysTheme())
        }
    }

    private fun setTheme(isDarkMode: Boolean?) {
        if (isDarkMode == null) return
        if (isDarkMode == false) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun getSysTheme(): Boolean {
        val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkModeOn = darkModeFlags == Configuration.UI_MODE_NIGHT_YES
        return isDarkModeOn
    }

    private fun isDark(): Boolean? {
        return sharedPreferences?.getBoolean("theme", false)
    }

    private fun setDarkMode(isDarkMode: Boolean?) {
        sharedPreferences?.edit()?.putBoolean("theme", isDarkMode ?: false)?.apply()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.applyLocale(newBase))
    }

}