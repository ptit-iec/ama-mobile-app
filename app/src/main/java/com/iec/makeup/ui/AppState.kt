package com.iec.makeup.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.iec.makeup.ui.navigation.TopLevelDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Composable
fun rememberMakeupAppState(
    navController: NavController,
): MakeupAppState {
    return remember(navController) {
        MakeupAppState(navController)
    }
}

class MakeupAppState(
    private val navController: NavController,
) {

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuth get() = _isAuthenticated.asStateFlow()

    private var _isShownBottomNav = MutableStateFlow(false)
    val isShownBottomNav = _isShownBottomNav.asStateFlow()

    private var _currentTopLevelDestination = MutableStateFlow(TopLevelDestination.Page1)
    val currentTopLevelDestination = _currentTopLevelDestination.asStateFlow()

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        if (currentTopLevelDestination.value != destination) {
            _currentTopLevelDestination.value = destination
            navController.navigate(destination.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    private fun checkIfConnected(): Boolean {
        return true
    }

    fun setVisibleBottomNav(isVisible: Boolean) {
        _isShownBottomNav.value = isVisible
    }

}