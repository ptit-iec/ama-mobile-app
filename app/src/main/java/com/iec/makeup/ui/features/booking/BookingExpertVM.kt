package com.iec.makeup.ui.features.booking

import com.iec.makeup.core.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.Reducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

// Data classes for State, Events, and Effects
@Serializable
data class BookingScreenState(
    val isLoading: Boolean = false,
    val userName: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val expert: String? = null,
    val serviceType: String? = null,
    val dateTime: String? = null,
    val makeupType: String? = null,
    val makeupPrice: String? = null,
    val travelCost: String? = null,
    val totalAmount: String? = null,
    val serviceTypes: List<String>? = null,
    val makeupTypes: List<String>? = null,
    val experts: List<String>? = null
) : Reducer.ViewState

sealed class BookingScreenEvent() : Reducer.ViewEvent {
    data class OnAddressEdited(val newAddress: String) : BookingScreenEvent()
    data class OnPhoneNumberEdited(val newPhoneNumber: String) : BookingScreenEvent()
    data class OnExpertSelected(val expert: String) : BookingScreenEvent()
    data class OnServiceTypeSelected(val serviceType: String) : BookingScreenEvent()
    data class OnDateTimeSelected(val dateTime: String) : BookingScreenEvent()
    data class OnMakeupTypeSelected(val makeupType: String) : BookingScreenEvent()
    data class OnMakeupPriceChanged(val price: String) : BookingScreenEvent() // If editable
    data class OnTravelCostChanged(val cost: String) : BookingScreenEvent() // If editable
    data object OnConfirmBooking : BookingScreenEvent()
    data class OnLoading(val isLoading: Boolean) : BookingScreenEvent()
    data class OnError(val message: String?) : BookingScreenEvent()
    data class OnInitData(
        val address: String,
        val phoneNumber: String,
        val expert: String,
        val serviceType: String,
        val dateTime: String,
        val makeupType: String,
        val makeupPrice: String,
        val travelCost: String,
        val serviceTypes: List<String>,
        val makeupTypes: List<String>,
        val experts: List<String>
    ) : BookingScreenEvent()
}

sealed class BookingScreenEffect() : Reducer.ViewEffect {
    data class ShowError(val message: String) : BookingScreenEffect()
    data class ShowToast(val message: String) : BookingScreenEffect()
    data object NavigateToConfirmation : BookingScreenEffect() // Example navigation effect
    data object ShowConfirmationDialog : BookingScreenEffect() // Example dialog effect
}

// Reducer
class BookingScreenReducer() : Reducer<BookingScreenState, BookingScreenEvent, BookingScreenEffect> {
    override fun reduce(
        currentState: BookingScreenState,
        event: BookingScreenEvent
    ): Pair<BookingScreenState, BookingScreenEffect?> {
        return when (event) {
            is BookingScreenEvent.OnAddressEdited -> {
                currentState.copy(address = event.newAddress) to null
            }
            is BookingScreenEvent.OnPhoneNumberEdited -> {
                currentState.copy(phoneNumber = event.newPhoneNumber) to null
            }
            is BookingScreenEvent.OnExpertSelected -> {
                // In a real app, selecting an expert might affect the price or available times
                currentState.copy(expert = event.expert) to null
            }
            is BookingScreenEvent.OnServiceTypeSelected -> {
                // Selecting service type might affect price or travel cost
                currentState.copy(serviceType = event.serviceType) to null
            }
            is BookingScreenEvent.OnDateTimeSelected -> {
                // Selecting date/time might affect expert availability
                currentState.copy(dateTime = event.dateTime) to null
            }
            is BookingScreenEvent.OnMakeupTypeSelected -> {
                // Selecting makeup type likely affects the makeup price
                val newMakeupPrice = when (event.makeupType) {
                    "Dự tiệc" -> "350.000"
                    "Cô dâu" -> "1.500.000" // Example price change
                    "Hàng ngày" -> "200.000" // Example price change
                    else -> "0"
                }
                currentState to null
            }
            is BookingScreenEvent.OnMakeupPriceChanged -> {
                val cleanedPrice = event.price.filter { it.isDigit() }
                currentState to null
            }
            is BookingScreenEvent.OnTravelCostChanged -> {
                val cleanedCost = event.cost.filter { it.isDigit() }
                currentState to null
            }
            BookingScreenEvent.OnConfirmBooking -> {
                // Trigger a side effect, like showing a confirmation dialog or navigating
                currentState.copy(isLoading = true) to BookingScreenEffect.ShowConfirmationDialog // Or NavigateToConfirmation
            }
            is BookingScreenEvent.OnLoading -> {
                currentState.copy(isLoading = event.isLoading) to null
            }
            is BookingScreenEvent.OnError -> {
                currentState.copy(isLoading = false) to BookingScreenEffect.ShowError(event.message ?: "Unknown error")
            }
            is BookingScreenEvent.OnInitData -> {
                currentState.copy(
                    isLoading = false,
                    address = event.address,
                    phoneNumber = event.phoneNumber,
                    expert = event.expert,
                    serviceType = event.serviceType,
                    dateTime = event.dateTime,
                    makeupType = event.makeupType,
                    makeupPrice = event.makeupPrice,
                    travelCost = event.travelCost,
                    serviceTypes = event.serviceTypes,
                    makeupTypes = event.makeupTypes,
                    experts = event.experts,
                    totalAmount = calculateTotal(event.makeupPrice.filter { it.isDigit() }, event.travelCost.filter { it.isDigit() })
                ) to null
            }
        }
    }

    private fun calculateTotal(makeupPrice: String, travelCost: String): String {
        val price = makeupPrice.toLongOrNull() ?: 0L
        val cost = travelCost.toLongOrNull() ?: 0L
        return (price + cost).toString() // Return as string, format in UI
    }
}

// ViewModel
@HiltViewModel
class BookingScreenVM @Inject constructor(
    // Inject any necessary dependencies here, e.g., a booking repository
    // private val bookingRepository: BookingRepository
) : BaseViewModel<BookingScreenState, BookingScreenEvent, BookingScreenEffect>(
    initialState = BookingScreenState(),
    reducer = BookingScreenReducer()
) {

    init {
        // Load initial data when the ViewModel is created
        loadBookingDetails()
    }

    private fun loadBookingDetails() {
        // In a real application, you would fetch data from a repository or API
        viewModelScope.launch {
            sendEvent(BookingScreenEvent.OnLoading(true))
            try {
                // Simulate fetching data
                kotlinx.coroutines.delay(1000) // Simulate network delay

                val initialData = BookingScreenEvent.OnInitData(
                    address = "122 Hoàng Quốc Việt, Cổ Nhuế,\nCầu Giấy, Hà Nội",
                    phoneNumber = "0828421384",
                    expert = "Bùi Mai Linh",
                    serviceType = "Tại nhà",
                    dateTime = "06/02/2025 20:00",
                    makeupType = "Dự tiệc",
                    makeupPrice = "350.000",
                    travelCost = "0",
                    serviceTypes = listOf("Tại nhà", "Tại cửa hàng"),
                    makeupTypes = listOf("Dự tiệc", "Cô dâu", "Hàng ngày"),
                    experts = listOf("Bùi Mai Linh", "Nguyễn Văn A", "Trần Thị B")
                )
                sendEvent(initialData)
            } catch (e: Exception) {
                sendEventWithEffect(BookingScreenEvent.OnError(e.message))
            }
        }
    }

    fun onAddressEdited(newAddress: String) {
        sendEvent(BookingScreenEvent.OnAddressEdited(newAddress))
    }

    fun onPhoneNumberEdited(newPhoneNumber: String) {
        sendEvent(BookingScreenEvent.OnPhoneNumberEdited(newPhoneNumber))
    }

    fun onExpertSelected(expert: String) {
        sendEvent(BookingScreenEvent.OnExpertSelected(expert))
    }

    fun onServiceTypeSelected(serviceType: String) {
        sendEvent(BookingScreenEvent.OnServiceTypeSelected(serviceType))
    }

    fun onDateTimeSelected(dateTime: String) {
        sendEvent(BookingScreenEvent.OnDateTimeSelected(dateTime))
    }

    fun onMakeupTypeSelected(makeupType: String) {
        sendEvent(BookingScreenEvent.OnMakeupTypeSelected(makeupType))
    }

    fun onMakeupPriceChanged(price: String) {
        sendEvent(BookingScreenEvent.OnMakeupPriceChanged(price))
    }

    fun onTravelCostChanged(cost: String) {
        sendEvent(BookingScreenEvent.OnTravelCostChanged(cost))
    }

    fun onConfirmBookingClick() {
        // In a real app, you would perform validation and then potentially call a booking API
        viewModelScope.launch {
            sendEvent(BookingScreenEvent.OnConfirmBooking)
            // Simulate booking process
            kotlinx.coroutines.delay(1500) // Simulate API call delay
            // After successful booking:
            // sendEventWithEffect(BookingScreenEffect.NavigateToConfirmation)
            // Or if validation fails:
            // sendEventWithEffect(BookingScreenEffect.ShowError("Booking failed. Please try again."))
            sendEvent(BookingScreenEvent.OnLoading(false)) // Hide loading after process
        }
    }

    // Helper to format the total amount with VNĐ
    fun formatTotalAmount(amount: String): String {
        // Basic formatting, you might need a more robust solution for larger numbers and locales
        return "${amount.replace(".","")}VNĐ"
    }
}