package com.vasifgumbatov.news.view.ui.profile.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeVM @Inject constructor() : ViewModel() {

    private var isDarkMode: Boolean? = null
    private val _theme: MutableLiveData<Boolean?> = MutableLiveData(null)

    val theme: LiveData<Boolean?> = _theme

    fun setDarkMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
        _theme.value = isDarkMode
    }

    fun changeTheme() {
        this.isDarkMode = isDarkMode?.not()
        _theme.value = isDarkMode
    }
}