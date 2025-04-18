package com.iec.makeup.ui.features.authentication.register

import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.ui.features.authentication.login.LoginScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class RegisterState(
    val isLoading: Boolean = false,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val error: String? = null
) : Reducer.ViewState


sealed class RegisterScreenEvent : Reducer.ViewEvent {
    data object DoSignUp : RegisterScreenEvent()
    data object ForgotPassword : RegisterScreenEvent()
    data class OnUsernameChange(val username: String) : RegisterScreenEvent()
    data class OnEmailChange(val email: String) : RegisterScreenEvent()
    data class OnPasswordChange(val password: String) : RegisterScreenEvent()
    data class OnLoadingDialog(val loading: Boolean) : RegisterScreenEvent()
    data class OnShowError(val message: String?) : RegisterScreenEvent()
}

sealed class RegisterScreenEffect : Reducer.ViewEffect {
    data class OnShowError(val message: String?) : RegisterScreenEffect()
    data class OnShowToast(val message: String) : RegisterScreenEffect()
    data object NavToLogin : RegisterScreenEffect()
}

class RegisterScreenReducer : Reducer<RegisterState, RegisterScreenEvent, RegisterScreenEffect> {
    override fun reduce(
        currentState: RegisterState,
        event: RegisterScreenEvent
    ): Pair<RegisterState, RegisterScreenEffect?> {
        return when (event) {
            is RegisterScreenEvent.DoSignUp -> {
                return currentState to null
            }
            is RegisterScreenEvent.ForgotPassword -> {
                return currentState to RegisterScreenEffect.NavToLogin
            }
            is RegisterScreenEvent.OnEmailChange -> {
                return currentState.copy(
                    email = event.email
                ) to null
            }
            is RegisterScreenEvent.OnLoadingDialog -> {
                return currentState.copy(
                    isLoading = event.loading
                ) to null
            }
            is RegisterScreenEvent.OnPasswordChange -> {
                return currentState.copy(
                    password = event.password
                ) to null
            }
            is RegisterScreenEvent.OnShowError -> {
                return currentState to RegisterScreenEffect.OnShowError(event.message)
            }
            is RegisterScreenEvent.OnUsernameChange -> {
                return currentState.copy(
                    username = event.username
                ) to null
            }
        }
    }

}


@HiltViewModel
class RegisterScreenVM @Inject constructor() : BaseViewModel<RegisterState, RegisterScreenEvent, RegisterScreenEffect>(
    initialState = RegisterState(),
    reducer = RegisterScreenReducer()
) {

    fun updateLoadingVisible(isLoading: Boolean){
        sendEvent(RegisterScreenEvent.OnLoadingDialog(isLoading))
    }

    fun inputUsername(str: String) {
        sendEvent(RegisterScreenEvent.OnUsernameChange(str))
    }
    fun inputPassword(str: String) {
        sendEvent(RegisterScreenEvent.OnPasswordChange(str))
    }
    fun inputEmail(str: String) {
        sendEvent(RegisterScreenEvent.OnEmailChange(str))
    }

    fun doSignUp(){
        showErrorMessage("In development")
    }

    fun showErrorMessage(message: String?){
        sendEventWithEffect(RegisterScreenEvent.OnShowError(message))
    }
    val hideErrorMessage: Unit = showErrorMessage(null)
}