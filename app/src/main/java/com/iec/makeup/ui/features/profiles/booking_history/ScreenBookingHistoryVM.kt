package com.iec.makeup.ui.features.profiles.booking_history

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.data.remote.dto.UserBookingDTO
import com.iec.makeup.data.repository.BookingRepository
import com.iec.makeup.data.repository.ExpertRepository
import com.iec.makeup.data.repository.MakeUpTemplateRepository
import com.iec.makeup.ui.features.profiles.booking_history.model.HistoryCategory
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ScreenBookingHistoryState(
    val isLoading: Boolean = false,
    val data: List<UserBookingUIWrapper>? = null,
    val error: String? = null,
) : Reducer.ViewState

sealed class ScreenBookingHistoryEvent : Reducer.ViewEvent {
    data class OnLoadData(val data: List<UserBookingUIWrapper>) : ScreenBookingHistoryEvent()
    data class OnError(val message: String?) : ScreenBookingHistoryEvent()
    data class OnLoading(val isLoading: Boolean) : ScreenBookingHistoryEvent()
}

sealed class ScreenBookingHistoryEffect : Reducer.ViewEffect {
    data class OnShowToast(val message: String) : ScreenBookingHistoryEffect()
    data class OnNavigateToDetail(val id: String) : ScreenBookingHistoryEffect()
    data class OnShowError(val message: String?) : ScreenBookingHistoryEffect()
}


class ScreenBookingHistoryReducer :
    Reducer<ScreenBookingHistoryState, ScreenBookingHistoryEvent, ScreenBookingHistoryEffect> {
    override fun reduce(
        state: ScreenBookingHistoryState,
        event: ScreenBookingHistoryEvent
    ): Pair<ScreenBookingHistoryState, ScreenBookingHistoryEffect?> {
        return when (event) {
            is ScreenBookingHistoryEvent.OnLoadData -> {
                state.copy(
                    isLoading = false,
                    data = event.data
                ) to null
            }

            is ScreenBookingHistoryEvent.OnError -> {
                state.copy(
                    isLoading = false,
                    error = event.message
                ) to ScreenBookingHistoryEffect.OnShowError(event.message)
            }

            is ScreenBookingHistoryEvent.OnLoading -> {
                state.copy(
                    isLoading = event.isLoading
                ) to null
            }

        }
    }
}

@HiltViewModel
class ScreenBookingHistoryVM @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val expertRepository: ExpertRepository,
    private val makeUpTemplateRepository: MakeUpTemplateRepository
) :
    BaseViewModel<ScreenBookingHistoryState, ScreenBookingHistoryEvent, ScreenBookingHistoryEffect>(
        initialState = ScreenBookingHistoryState(),
        reducer = ScreenBookingHistoryReducer()
    ) {
        init {
            sendEvent(ScreenBookingHistoryEvent.OnLoading(true))
            bookingHistory().flatMapConcat { it ->
               flowGetExperts(it)
            }.onEach { userBookingUIWrapper ->
                Log.d(TAG, userBookingUIWrapper.toString())
                sendEvent(ScreenBookingHistoryEvent.OnLoadData(userBookingUIWrapper))
            }
                .catch { sendEventWithEffect(ScreenBookingHistoryEvent.OnError(it.message)) }
                .launchIn(viewModelScope)
        }
    private fun bookingHistory() = callbackFlow {
        val result = bookingRepository.getUserBooking()
        if (result != null) {
            trySend(result)
            close()
        }else{
            throw Exception("Can't get booking history")
        }
        awaitClose {  }
    }

    private fun getExpertByIDRemote(id: String) = callbackFlow {
        val result = expertRepository.getExpertByID(id)
        if (result != null) {
            trySend(result)
            close()
        }else{
            throw Exception("Can't get expert ")
        }
        awaitClose {  }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun flowGetExperts(ids: List<UserBookingDTO>): Flow<List<UserBookingUIWrapper>> = flow {
        val list: MutableList<UserBookingUIWrapper> = mutableListOf()
        ids.asFlow().flatMapMerge { booking ->
            getExpertByIDRemote(id = booking.expertId ?: "").map { expertDetail ->
                UserBookingUIWrapper(
                    id = booking.Id ?: "",
                    name = expertDetail.name ?: "",
                    type = booking.serviceType ?: "",
                    totalBill = booking.makeupPrice.toString(),
                    state = when(booking.status){
                        "pending" -> HistoryCategory.PENDING
                        "confirmed" -> HistoryCategory.CONFIRMED
                        "completed" -> HistoryCategory.COMPLETED
                        "cancelled" -> HistoryCategory.CANCELED
                        else -> HistoryCategory.PENDING
                    },
                    bookedDate = booking.bookingTime ?: "",
                    address = booking.address ?: "",
                    phone = booking.phone ?: "",
                    note = booking.note ?: "",
                    cancelReason = booking.cancelReason ?: "",
                    image = makeUpTemplateRepository.getMakeUpTemplateById(booking.makeupTempId ?: "")?.thumbnail ?: "")
            }
        }
            .onEach { Log.d("TAG", "Added item: $it") }
            .onCompletion { Log.d("TAG", "Flow completed") }
            .collect {
                list.add(it)
            }
        Log.d("TAG", "flowGetExperts: $list")
        emit(list)
    }

    companion object {
        val TAG: String = ScreenBookingHistoryVM::class.java.simpleName
    }
}