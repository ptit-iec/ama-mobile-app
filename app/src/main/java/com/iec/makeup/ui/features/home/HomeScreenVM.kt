package com.iec.makeup.ui.features.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.User
import com.iec.makeup.data.remote.api.UserEndpoint
import com.iec.makeup.data.remote.dto.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeScreenState(
    val isLoading: Boolean = false,
    val userProfile: User? = null,
    val error: String? = null,
    val isRefreshing: Boolean = false,

    // Order Section
    val orderToPay: List<String> = emptyList(),
    val orderToReceive: List<String> = emptyList(),
    val orderToReview: List<String> = emptyList(),

    // Reels Section
) : Reducer.ViewState


sealed class HomeScreenEvent : Reducer.ViewEvent {
    data class LoadInitData(val user: User) : HomeScreenEvent()
}

sealed class HomeScreenEffect : Reducer.ViewEffect {

}


class HomeScreenReducer : Reducer<HomeScreenState, HomeScreenEvent, HomeScreenEffect> {
    override fun reduce(
        currentState: HomeScreenState,
        event: HomeScreenEvent
    ): Pair<HomeScreenState, HomeScreenEffect?> {
        return when (event) {
            is HomeScreenEvent.LoadInitData -> {
                currentState.copy(
                    userProfile = event.user
                ) to null
            }

            else -> currentState to null
        }
    }

}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val userEndpoint: UserEndpoint
) : BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState(),
    reducer = HomeScreenReducer()
) {
    init {
        viewModelScope.launch {
            val a = userEndpoint.getUsers()
            if (a.isSuccessful) {
                Log.d("HomeScreenVM", "init: ${a.body()}")
                val data = a.body()
                if (data != null) {
                    if (data.success == true) {
                        sendEvent(HomeScreenEvent.LoadInitData(data.data!!.toUser()))
                    }
                }
            }
        }
    }
}