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
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.DialogCompose
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
    navToHome: () -> Unit = {}
) {
    val context = LocalContext.current
    var errorShown: String? by rememberSaveable { mutableStateOf(null) }
    val scope = rememberCoroutineScope() + CoroutineExceptionHandler { _, throwable ->
        errorShown = throwable.message
    }
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                withTimeout(GOOGLE_AUTH_TIMEOUT){
                    delay(5100)
                }
            }catch (e: TimeoutCancellationException){
                errorShown = e.message
            }
        }
    }

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
    if (errorShown != null) {
        DialogCompose(
            text = errorShown ?: "Something went wrong, please try again later",
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