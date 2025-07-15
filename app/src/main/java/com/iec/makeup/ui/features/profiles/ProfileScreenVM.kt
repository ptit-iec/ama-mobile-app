package com.iec.makeup.ui.features.profiles


import android.util.Log
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.User
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.core.network.TokenManager
import com.iec.makeup.data.remote.api.UserEndpoint
import com.iec.makeup.data.remote.dto.MakeUpTemplateCategoryDTO
import com.iec.makeup.data.remote.dto.toUser
import com.iec.makeup.data.repository.ExpertRepository
import com.iec.makeup.data.repository.MakeUpTemplateCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


data class ProfileScreenState(
    val isLoading: Boolean = false,
    val userProfile: User? = null,
    val error: String? = null,
    val isRefreshing: Boolean = false,

    ) : Reducer.ViewState


sealed class ProfileScreenEvent : Reducer.ViewEvent {
    data class LoadInitData(
        val user: User,
    ) : ProfileScreenEvent()

    data class OnLoading(val isLoading: Boolean) : ProfileScreenEvent()
    data class OnShowError(val error: String?) : ProfileScreenEvent()
}

sealed class HomeScreenEffect : Reducer.ViewEffect {
    data class OnShowError(val error: String?) : HomeScreenEffect()
}


class ProfileScreenReducer : Reducer<ProfileScreenState, ProfileScreenEvent, HomeScreenEffect> {
    override fun reduce(
        currentState: ProfileScreenState,
        event: ProfileScreenEvent
    ): Pair<ProfileScreenState, HomeScreenEffect?> {
        return when (event) {
            is ProfileScreenEvent.LoadInitData -> {
                currentState.copy(
                    userProfile = event.user,
                ) to null
            }

            is ProfileScreenEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading
                ) to null
            }

            is ProfileScreenEvent.OnShowError -> {
                currentState.copy(
                    error = event.error
                ) to HomeScreenEffect.OnShowError(event.error)
            }

            else -> currentState to null
        }
    }

}


@HiltViewModel
class ProfileScreenVM @Inject constructor(
    private val userEndpoint: UserEndpoint,
    private val tokenManager: TokenManager
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, HomeScreenEffect>(
    initialState = ProfileScreenState(),
    reducer = ProfileScreenReducer()
) {
    init {
        sendEvent(ProfileScreenEvent.OnLoading(true))
        getUser().onEach { user ->
            if (user == null) {
                sendEventWithEffect(ProfileScreenEvent.OnShowError("User not found"))
            } else {
                sendEvent(ProfileScreenEvent.LoadInitData(user))
            }
            sendEvent(ProfileScreenEvent.OnLoading(false))
        }.catch {
            sendEventWithEffect(ProfileScreenEvent.OnShowError(it.message))
        }
            .launchIn(viewModelScope)

    }

    private fun getUser() = callbackFlow {
        val a = userEndpoint.getUsers()
        if (a.isSuccessful) {
            val data = a.body()
            if (data != null && data.success == true) {
                trySend(data.data?.toUser())
            }
        }
        awaitClose {

        }
    }

    fun logout() {
        tokenManager.deleteToken()
    }

}