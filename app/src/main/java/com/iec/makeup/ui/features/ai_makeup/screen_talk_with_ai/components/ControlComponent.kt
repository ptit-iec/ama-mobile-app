package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.R
import com.iec.makeup.utils.SpeechToTextHelper


@Composable
fun ControlComponent(
    onOpenChat: () -> Unit = {},
    onCancelCall: () -> Unit = {},
    onReceiveText: (String) -> Unit = {}
) {
    var isUserSpeaking by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val speechToText = remember { SpeechToTextHelper(context) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(isUserSpeaking){
            speechToText.startListening {
                onReceiveText(it)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(64.dp)
                    .clickable {
                        onOpenChat()
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.chat),
                    contentDescription = "Chat",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Chat",
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.voice),
                    contentDescription = "Chat",
                    modifier = Modifier
                        .size(96.dp)
                        .pointerInput(Unit) {
                            while (true) {
                                awaitPointerEventScope {
                                    // Wait for user to touch down
                                    val down = awaitFirstDown()
                                    isUserSpeaking = true
                                    // Wait until the user lifts their finger or drag is canceled
                                    var holding = true
                                    while (holding) {
                                        val event = awaitPointerEvent()
                                        holding = event.changes.any { it.pressed }
                                    }

                                    isUserSpeaking = false
                                }
                            }
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (!isUserSpeaking) "Hold to Speak" else "Release to Stop",
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(64.dp)
                    .clickable {
                        onCancelCall()
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.cross),
                    contentDescription = "Chat",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cancel",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ControlComponent()
}