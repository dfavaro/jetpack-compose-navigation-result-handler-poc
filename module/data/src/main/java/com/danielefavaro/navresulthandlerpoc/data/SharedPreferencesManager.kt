package com.danielefavaro.navresulthandlerpoc.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    /** Function to get a value by key with a default */
    fun getString(key: String, default: String): String {
        return sharedPreferences.getString(key, default) ?: default
    }

    /** Function to save a value */
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /** Function to listen for changes on a specific key as a Flow */
    fun observeKey(key: String): Flow<String> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                sharedPreferences.getString(key, null)?.let { trySend(it) }
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        // Clean up when the flow is canceled
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
}