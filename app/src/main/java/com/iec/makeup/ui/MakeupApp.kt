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

val LocalAppState = compositionLocalOf<MakeupAppState> {
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
    LaunchedEffect(Unit) {
        Log.d(TAG, "Trigger Recompose")
    }


    return CompositionLocalProvider(LocalAppState provides appState ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = ColorFAF9F9)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                // Create references for the composables to constrain
                val (nav, bottomBar) = createRefs()

                // NavigationGraph
                Box(
                    modifier = Modifier
                        .constrainAs(nav) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(if (isShowBottomNav.value) bottomBar.top else parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                ) {
                    NavigationGraph(navController = navController, appState = appState)
                }

                // BottomNavigationBar (conditionally shown)
                if (isShowBottomNav.value) {
                    Box(
                        modifier = Modifier
                            .constrainAs(bottomBar) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        BottomNavigationBar(
                            onTopLevelClick = {
                                appState.navigateToTopLevelDestination(it)
                            },
                            currentDestination = currDestination.value
                        )
                    }
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
        }
    }

}