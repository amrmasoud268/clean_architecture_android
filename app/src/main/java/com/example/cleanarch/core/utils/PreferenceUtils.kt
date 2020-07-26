package com.example.cleanarch.core.utils

import android.content.SharedPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject


object PreferenceUtils : KoinComponent {

    val preferenceManager : SharedPreferences by inject()

    fun save(key : String , value : Any){
        when (value) {
            is String -> preferenceManager.edit().putString(key, value).apply()
            is Int -> preferenceManager.edit().putInt(key, value).apply()
            is Boolean -> preferenceManager.edit().putBoolean(key, value).apply()
            is Float -> preferenceManager.edit().putFloat(key, value).apply()
            is Long -> preferenceManager.edit().putLong(key, value).apply()
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    inline fun <reified T : Any> get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> preferenceManager.getString(key, defaultValue as? String) as T?
            Int::class -> preferenceManager.getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> preferenceManager.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> preferenceManager.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> preferenceManager.getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}