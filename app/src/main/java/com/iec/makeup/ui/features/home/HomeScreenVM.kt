package com.iec.makeup.ui.features.home

import android.util.Log
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.User
import com.iec.makeup.core.model.ui.Expert
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

    // Makeup Template Category
    val listMakeUpTemplateCategory: List<MakeUpTemplateCategoryDTO> = emptyList(),
    val listExpert: List<Expert> = emptyList()
) : Reducer.ViewState


sealed class HomeScreenEvent : Reducer.ViewEvent {
    data class LoadInitData(
        val user: User,
        val listMakeUpTemplateCategory: List<MakeUpTemplateCategoryDTO> = emptyList(),
        val listExpert: List<Expert> = emptyList()
    ) : HomeScreenEvent()
    data class OnLoading(val isLoading: Boolean) : HomeScreenEvent()
    data class OnShowError(val error: String?) : HomeScreenEvent()
}

sealed class HomeScreenEffect : Reducer.ViewEffect {
    data class OnShowError(val error: String?) : HomeScreenEffect()
}


class HomeScreenReducer : Reducer<HomeScreenState, HomeScreenEvent, HomeScreenEffect> {
    override fun reduce(
        currentState: HomeScreenState,
        event: HomeScreenEvent
    ): Pair<HomeScreenState, HomeScreenEffect?> {
        return when (event) {
            is HomeScreenEvent.LoadInitData -> {
                currentState.copy(
                    userProfile = event.user,
                    listMakeUpTemplateCategory = event.listMakeUpTemplateCategory,
                    listExpert = event.listExpert
                    ) to null
            }

            is HomeScreenEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading
                ) to null
            }
            is HomeScreenEvent.OnShowError -> {
                currentState.copy(
                    error = event.error
                ) to HomeScreenEffect.OnShowError(event.error)
            }
            else -> currentState to null
        }
    }

}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val userEndpoint: UserEndpoint,
    private val makeUpTemplateCategory: MakeUpTemplateCategoryRepository,
    private val expertRepository: ExpertRepository
) : BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState(),
    reducer = HomeScreenReducer()
) {
    init {
        sendEvent(HomeScreenEvent.OnLoading(true))
        combine(
            getAllMakeUpTemplateCategory(),
            getAllExperts(),
            getUser(),
        ) { makeUpTemplateCategory, listExpert, user ->
            Log.d("HomeScreenVM", "combine: $makeUpTemplateCategory $listExpert")
            if(user == null){
                sendEventWithEffect(HomeScreenEvent.OnShowError("User not found"))
            }
            else{
                sendEvent(HomeScreenEvent.LoadInitData(user, makeUpTemplateCategory, listExpert))
            }
            sendEvent(HomeScreenEvent.OnLoading(false))
        }
            .catch {
                sendEventWithEffect(HomeScreenEvent.OnShowError(it.message))
            }
            .launchIn(viewModelScope)
    }

    private fun getAllMakeUpTemplateCategory() = callbackFlow {
        trySend(makeUpTemplateCategory.getAllMakeUpTemplateCategory())
        awaitClose{

        }
    }
    private fun getUser()  = callbackFlow {
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

    private fun getAllExperts() = callbackFlow {
        val list = expertRepository.getAllExperts(
            isFirsTimeCall = true
        )
        trySend(list)
        awaitClose {

        }
    }
}