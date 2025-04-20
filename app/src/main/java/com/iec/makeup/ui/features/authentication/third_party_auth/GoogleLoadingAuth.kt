package com.iec.makeup.ui.features.authentication.third_party_auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iec.makeup.core.PersistentState
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.features.authentication.third_party_auth.viewmodel.GoogleAuthLoadingVM
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withTimeout

const val GOOGLE_AUTH_TIMEOUT = 5000L

@Composable
fun GoogleAuthLoadingScreen(
    navBack: () -> Unit = {},
    token: String? = null,
    navToHome: () -> Unit = {}
) {

    val viewModel: GoogleAuthLoadingVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val error = state.value.error
    val isLoading = state.value.isLoading
    LaunchedEffect(Unit) {
        if (token != null) {
            viewModel.doGoogleLogin(token)
        } else {
            viewModel.setError("Can't get token")
        }
    }
    LaunchedEffect(state.value.isSuccessLogin) {
        if (state.value.isSuccessLogin) {
            navToHome()
        }
    }

    if (isLoading) {
        // Use a Column to arrange elements vertically and fill the entire screen
        Column(
            modifier = Modifier.fillMaxSize(), // Take up all available space
            verticalArrangement = Arrangement.Center, // Center content vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
        ) {
            // The circular loading indicator
            AtomicLoadingDialog()

            // Add some space between the indicator and the text
            Spacer(modifier = Modifier.height(16.dp))

            // Text indicating the purpose of the loading state
            Text(
                text = "Verifying Google Sign-In...",
                style = MaterialTheme.typography.bodyLarge, // Use Material theme's typography
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f) // Slightly muted text color
            )
        }
    }
    if (error != null) {
        DialogCompose(
            text = error ?: "Something went wrong, please try again later",
            positiveAction = {
                navBack()
            }
        )
    }
}

@Preview
@Composable
private fun GooglePrevie() {
    GoogleAuthLoadingScreen()
}