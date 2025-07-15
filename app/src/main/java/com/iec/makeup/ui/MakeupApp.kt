package com.iec.makeup.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.navigation.BottomNavigationBar
import com.iec.makeup.ui.navigation.NavigationGraph
import com.iec.makeup.ui.theme.ColorFAF9F9


const val TAG = "MakeupApp"

val LocalAppState = compositionLocalOf< MakeupAppState> {
    error("CompositionLocal LocalAppState not present")
}

@Composable
fun MakeupApp(
    navController: NavHostController
) {
    val appState = remember { MakeupAppState(navController) }
    val isShowBottomNav = appState.isShownBottomNav.collectAsStateWithLifecycle()
    val currDestination = appState.currentTopLevelDestination.collectAsStateWithLifecycle()
    val isError = remember { mutableStateOf<String?>(null) }
    val isLoading = appState.isLoading.collectAsStateWithLifecycle()
    val isFaded = appState.fadedBackground.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        Log.d(TAG, "Trigger Recompose")
    }


    return CompositionLocalProvider(LocalAppState provides appState ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = ColorFAF9F9)
        ) {
            // NavigationGraph
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (isShowBottomNav.value) 50.dp else 0.dp)
            ) {
                NavigationGraph(navController = navController, appState = appState)
            }

            // BottomNavigationBar (conditionally shown)4
            if (isShowBottomNav.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    BottomNavigationBar(
                        onTopLevelClick = {
                            appState.navigateToTopLevelDestination(it)
                        },
                        currentDestination = currDestination.value
                    )
                }
            }
            if (isError.value != null) {
                DialogCompose(
                    text = "${isError.value}",
                    onCloseAction = {
                        isError.value = null
                    }
                )
            }
            AnimatedVisibility(
                visible = isLoading.value,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    AtomicLoadingDialog()
                }
            }

            if(isFaded.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ){

                }
            }
        }
    }

}