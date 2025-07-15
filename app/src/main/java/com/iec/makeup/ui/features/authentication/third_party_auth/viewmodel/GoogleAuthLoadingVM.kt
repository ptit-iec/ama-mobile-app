package com.iec.makeup.ui.features.authentication.third_party_auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.DataStoreInterface
import com.iec.makeup.core.PersistentState
import com.iec.makeup.core.PreferenceKeys
import com.iec.makeup.core.network.TokenManager
import com.iec.makeup.core.utils.fromJson
import com.iec.makeup.data.remote.api.AuthEndpoint
import com.iec.makeup.data.remote.api.UserEndpoint
import com.iec.makeup.data.repository.AuthRepository
import com.iec.makeup.network.APIResult
import com.iec.makeup.ui.features.authentication.login.LoginScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject



data class GoogleAuthLoadingState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccessLogin: Boolean = false
)


@HiltViewModel
class GoogleAuthLoadingVM @Inject constructor(
    private val dataStore: DataStoreInterface,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private var _state = MutableStateFlow(GoogleAuthLoadingState())
    var state = _state.asStateFlow()

    fun setError(error: String?) {
        _state.value = _state.value.copy(
            error = error
        )
    }

    fun doGoogleLogin(code: String) {
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            try {
                withTimeout(30000) {
                    val result =
                        authRepository.doGoogleLogin(code)
                    if (result.isSuccessful) {
                        tokenManager.setToken(
                            result.body()!!.data?.accessToken ?: ""
                        )
                        _state.value = _state.value.copy(
                            isSuccessLogin = true
                        )
                    } else {
                        try {
                            val error =
                                result.errorBody()?.string()?.fromJson<APIResult<String>>()
                            _state.value = _state.value.copy(
                                error = error?.message ?: "Unknown error"
                            )
                        } catch (e: Exception) {
                            _state.value = _state.value.copy(
                                error = e.toString() ?: "Unknown error"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.toString() ?: "Unknown error"
                )
            }
            _state.value = _state.value.copy(
                isLoading = false
            )

        }
    }

}