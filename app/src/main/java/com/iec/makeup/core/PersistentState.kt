package com.iec.makeup.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore("app")


object PreferenceKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PASSWORD = stringPreferencesKey("user_password")
    val USER_TOKEN = stringPreferencesKey("user_token")
}


interface DataStoreInterface {
    fun saveKey(key: Preferences.Key<String>, value: String)
    fun readKey(key: Preferences.Key<String>): Flow<String?>
    fun clearKey(vararg keys: Preferences.Key<String>)
}


class PersistentState @Inject constructor(
    @ApplicationContext val context: Context
) : DataStoreInterface {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: PersistentState? = null
        fun getInstance(context: Context): PersistentState {
            return PersistentState.Companion.INSTANCE ?: synchronized(this) {
                val instance = PersistentState(context)
                PersistentState.Companion.INSTANCE = instance
                instance
            }
        }
    }

    private val dataStore = context.appDataStore
    private val scope =
        CoroutineScope(Dispatchers.IO) + SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            Log.d("DataStoreImplement", exception.toString())
        }

    override fun saveKey(key: Preferences.Key<String>, value: String) {
        scope.launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }

    override fun readKey(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map {
            it[key]
        }
    }

    override fun clearKey(vararg keys: Preferences.Key<String>) {
        scope.launch {
            keys.forEach { key ->
                dataStore.edit { it.remove(key) }
            }
        }
    }


}