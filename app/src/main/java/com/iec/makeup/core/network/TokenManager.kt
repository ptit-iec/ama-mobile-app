package com.iec.makeup.core.network

import com.iec.makeup.core.DataStoreInterface
import com.iec.makeup.core.PreferenceKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(
    private val dataStore: DataStoreInterface,
) {
    fun setToken(token: String) = dataStore.saveKey(PreferenceKeys.USER_TOKEN, token)
    fun getToken(): Flow<String?> = dataStore.readKey(PreferenceKeys.USER_TOKEN)
}