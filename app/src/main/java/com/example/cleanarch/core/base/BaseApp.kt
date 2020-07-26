package com.example.cleanarch.core.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.cleanarch.core.utils.LocaleManager

open class BaseApp : Application() {

    companion object {
        lateinit var localeManager: LocaleManager
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

    }

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale(base))
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }


}