package com.iec.makeup.ui.features.authentication.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.DataStoreInterface
import com.iec.makeup.core.PreferenceKeys
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.utils.fromJson
import com.iec.makeup.data.repository.AuthRepository
import com.iec.makeup.network.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


data class LoginScreenState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val username: String?,
    val password: String?,
) : Reducer.ViewState

sealed class LoginScreenEvent() : Reducer.ViewEvent {
    data object Login : LoginScreenEvent()
    data object SignUp : LoginScreenEvent()
    data object ForgotPassword : LoginScreenEvent()
    data class OnUsernameChange(val username: String) : LoginScreenEvent()
    data class OnPasswordChange(val password: String) : LoginScreenEvent()
    data class OnLoadingDialog(val loading: Boolean) : LoginScreenEvent()
    data class OnShowError(val message: String?) : LoginScreenEvent()
}

sealed class LoginScreenEffect() : Reducer.ViewEffect {
    data class ShowError(val message: String?) : LoginScreenEffect()
    data class ShowToast(val message: String) : LoginScreenEffect()
    data object NavigateToHome : LoginScreenEffect()
    data object NavigateToSignUp : LoginScreenEffect()
}

class LoginScreenReducer @Inject constructor() :
    Reducer<LoginScreenState, LoginScreenEvent, LoginScreenEffect> {
    override fun reduce(
        currentState: LoginScreenState,
        event: LoginScreenEvent
    ): Pair<LoginScreenState, LoginScreenEffect?> {
        when (event) {
            LoginScreenEvent.ForgotPassword -> {
                return currentState to null
            }

            LoginScreenEvent.Login -> {
                return currentState to LoginScreenEffect.NavigateToHome
            }

            LoginScreenEvent.SignUp -> {
                return currentState to null
            }

            is LoginScreenEvent.OnPasswordChange -> {
                val newState = currentState.copy(
                    password = event.password
                )
                return newState to null
            }

            is LoginScreenEvent.OnUsernameChange -> {
                val newState = currentState.copy(
                    username = event.username
                )
                return newState to null
            }

            is LoginScreenEvent.OnLoadingDialog -> {
                val newState = currentState.copy(
                    isLoading = event.loading
                )
                return newState to null
            }

            is LoginScreenEvent.OnShowError -> {
                return currentState to LoginScreenEffect.ShowError(event.message)
            }
        }
    }

}

@HiltViewModel
class LoginScreenVM @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreInterface
) : BaseViewModel<LoginScreenState, LoginScreenEvent, LoginScreenEffect>(
    initialState = LoginScreenState(
        isLoading = false,
        isAuthenticated = false,
        username = null,
        password = null
    ),
    reducer = LoginScreenReducer()
) {
    private val coroutineScope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "coroutineScope: ${throwable.message}")
    }

    init {
        combine(
            dataStore.readKey(PreferenceKeys.USER_NAME),
            dataStore.readKey(PreferenceKeys.USER_PASSWORD)
        ) { email, password ->
            Pair(email, password)
        }.onEach {
            sendEvent(LoginScreenEvent.OnUsernameChange(it.first ?: ""))
            sendEvent(LoginScreenEvent.OnPasswordChange(it.second ?: ""))
        }.launchIn(
            viewModelScope + Dispatchers.IO
        )
    }

    fun doLogin() {
        coroutineScope.launch(Dispatchers.IO) {
            sendEvent(LoginScreenEvent.OnLoadingDialog(true))
            try {
                withTimeout(5000) {
                    val result =
                        authRepository.doLogin(state.value.username!!, state.value.password!!)
                    if (result.isSuccessful) {
                        dataStore.saveKey(PreferenceKeys.USER_NAME, state.value.username!!)
                        dataStore.saveKey(PreferenceKeys.USER_PASSWORD, state.value.password!!)
                        dataStore.saveKey(
                            PreferenceKeys.USER_TOKEN,
                            result.body()!!.data?.accessToken ?: ""
                        )
                        sendEventWithEffect(LoginScreenEvent.Login)
                    } else {
                        try {
                            val error =
                                result.errorBody()?.string()?.fromJson<APIResult<String>>()
                            errorArrived(
                                error?.message ?: "Unknown error"
                            )
                        } catch (e: Exception) {
                            errorArrived(e.toString())
                        }
                    }
                }
//                delay(2000)
//                sendEventWithEffect(LoginScreenEvent.Login)
            } catch (e: Exception) {
                errorArrived(e.toString())
            }
            sendEvent(LoginScreenEvent.OnLoadingDialog(false))
        }
    }

    fun errorArrived(message: String) = sendEventWithEffect(LoginScreenEvent.OnShowError(message))
    fun errorDismiss() = sendEventWithEffect(LoginScreenEvent.OnShowError(null))
    fun inputUsername(inputUsername: String) {
        sendEvent(LoginScreenEvent.OnUsernameChange(inputUsername))
    }

    fun inputPassword(inputPassword: String) {
        sendEvent(LoginScreenEvent.OnPasswordChange(inputPassword))
    }


    companion object {
        const val TAG = "LoginScreenVM"

    }
}