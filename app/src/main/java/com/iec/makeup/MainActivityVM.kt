package com.iec.makeup

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.iec.makeup.core.model.UserDefaultConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data class Success(val userConfig: UserDefaultConfig) : MainActivityUiState
    data class Error(val message: String) : MainActivityUiState
    fun isLoading() = this is Loading

}



@HiltViewModel
class MainActivityVM @Inject constructor() : ViewModel() {
    val uiState:  StateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState.Loading).asStateFlow()

}