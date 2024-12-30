package com.vasifgumbatov.news

import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import java.util.logging.Logger
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}