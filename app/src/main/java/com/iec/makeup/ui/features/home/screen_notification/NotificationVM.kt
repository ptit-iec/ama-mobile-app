package com.iec.makeup.ui.features.home.screen_notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.model.Notification
import com.iec.makeup.core.model.mockNotificationList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class NotificationVM @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    isLoading = true,
                )
            )
            delay(2000)
            _state.emit(
                _state.value.copy(
                    isLoading = false,
                    notifications = mockNotificationList
                )
            )
        }
    }
}