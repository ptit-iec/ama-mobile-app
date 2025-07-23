package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.iec.makeup.core.ui.ShimmerText
import kotlinx.coroutines.delay


@Composable
fun ProcessThinkingComponent(
    message: String = "Processing..."
) {
    var showText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        var i = 0
        while (showText.length < message.length) {
            showText += message[i++]
            delay(40)
        }
        delay(1000)
        showText = ""
    }
    ShimmerText(
        text = showText,
    )
}

@Preview
@Composable
fun ProcessThinkingComponentPreview() {
    ProcessThinkingComponent()
}