package com.iec.makeup.ui.features.home.screen_expert_detail_information

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.data.remote.dto.ExpertDetail
import com.iec.makeup.data.remote.dto.UserReviewExpertDTO
import com.iec.makeup.data.repository.ExpertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class StylistDetailScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val expertData: ExpertDetail? = null,
    val userReviews: List<UserReviewExpertDTO>? = null
) : Reducer.ViewState


sealed class StylistDetailScreenEffect : Reducer.ViewEffect{
    data class ShowToast(val message: String?) : StylistDetailScreenEffect()
    data class ShowError(val message: String?) : StylistDetailScreenEffect()
}

sealed class StylistDetailScreenEvent: Reducer.ViewEvent {
    data class OnExpertDataLoaded(val expertData: ExpertDetail) : StylistDetailScreenEvent()
    data class OnUserReviewsLoaded(val userReviews: List<UserReviewExpertDTO>) : StylistDetailScreenEvent()
    data class OnLoading(val loading: Boolean) : StylistDetailScreenEvent()
    data class OnError(val message: String?) : StylistDetailScreenEvent()
}

class StylistDetailScreenReducer : Reducer<StylistDetailScreenState, StylistDetailScreenEvent, StylistDetailScreenEffect> {
    override fun reduce(
        currentState: StylistDetailScreenState,
        event: StylistDetailScreenEvent
    ): Pair<StylistDetailScreenState, StylistDetailScreenEffect?> {
        return when (event) {
            is StylistDetailScreenEvent.OnError -> {
                currentState.copy(
                    isLoading = false,
                    error = event.message
                ) to StylistDetailScreenEffect.ShowError(event.message)
            }
            is StylistDetailScreenEvent.OnExpertDataLoaded -> {
                currentState.copy(
                    isLoading = false,
                    expertData = event.expertData
                ) to null
            }
            is StylistDetailScreenEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.loading
                ) to null
            }

            is StylistDetailScreenEvent.OnUserReviewsLoaded -> {
                currentState.copy(
                    isLoading = false,
                    userReviews = event.userReviews
                ) to null
            }
        }
    }

}


@HiltViewModel
class StylistDetailScreenVM @Inject constructor(
    private val expertRepository: ExpertRepository
) : BaseViewModel<StylistDetailScreenState, StylistDetailScreenEvent, StylistDetailScreenEffect>(
    initialState = StylistDetailScreenState(),
    reducer = StylistDetailScreenReducer()
) {

    private val scope = viewModelScope + CoroutineExceptionHandler {
            _, throwable ->
        sendEvent(StylistDetailScreenEvent.OnError(throwable.message ?: "Unknown error"))
    }

    fun initData(id: String){
        getExpertDetailInformation(id)
        getUserReviews(id)
    }


    private fun getUserReviews(id: String){
        sendEvent(StylistDetailScreenEvent.OnLoading(true))
        doGetUserReviews(id).onEach { response ->
            sendEvent(StylistDetailScreenEvent.OnUserReviewsLoaded(response))
            sendEvent(StylistDetailScreenEvent.OnLoading(false))
        }.launchIn(scope)
    }


    private fun getExpertDetailInformation(id: String){
        sendEvent(StylistDetailScreenEvent.OnLoading(true))
        doGetExpertById(id).onEach { response ->
            if(response != null){
                sendEvent(StylistDetailScreenEvent.OnExpertDataLoaded(response))  }
            else{
                sendEventWithEffect(StylistDetailScreenEvent.OnError("Can't get the user detail"))
            }
            sendEvent(StylistDetailScreenEvent.OnLoading(false))
        }.launchIn(scope)
    }

    fun dismissError(){
        sendEvent(StylistDetailScreenEvent.OnError(null))
    }

    private fun doGetExpertById(id: String) = callbackFlow {
        try {
            val response = expertRepository.getExpertByID(id)
            trySend(response)
        }catch (e: Exception){
            Log.d("StylistDetailScreenVM", "getExpertById: ${e.message}")
        }
        awaitClose{

        }
    }


    private fun doGetUserReviews(expertID: String) = callbackFlow {
        try {
            val response = expertRepository.getUserReviewsExpert(expertID)
            trySend(response)
        }catch (e: Exception){
            Log.d("StylistDetailScreenVM", "getUserReviews: ${e.message}")
        }
        awaitClose{

        }
    }

    fun onRefreshData(){

    }
}