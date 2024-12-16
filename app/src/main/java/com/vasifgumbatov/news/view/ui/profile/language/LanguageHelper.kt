package com.vasifgumbatov.news.view.ui.profile.language

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object LanguageHelper {
    private const val PREFS_NAME = "locale_prefs"
    private const val KEY_LANGUAGE = "language"

    // Initialize SharedPreferences
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Get the saved language, or default to English
    private fun getCurrentLanguage(context: Context): String {
        return getPrefs(context).getString(KEY_LANGUAGE, "en") ?: "en"
    }

    // Save the selected language
    private fun saveLanguage(context: Context, language: String) {
        getPrefs(context).edit().putString(KEY_LANGUAGE, language).apply()
    }

    // Set and save the locale
    fun setLocale(context: Context, language: String) {
        saveLanguage(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLocales(LocaleList.forLanguageTags(language))
        } else {
            configuration.locale = locale
        }

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    // Apply the saved locale on app start
    fun applyLocale(context: Context): Context {
        val language = getCurrentLanguage(context)
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLocales(LocaleList.forLanguageTags(language))
        } else {
            configuration.locale = locale
        }

        return context.createConfigurationContext(configuration)
    }
}