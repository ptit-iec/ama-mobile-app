package com.example.iec.ui.feature.main.message.box_chat_message

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.ChatMessageVM
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageScreenState
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.ChattingComponent
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageInput
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.TopAppBarMessage
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.UserStatus
import com.iec.makeup.ui.theme.ColorFFE4E1


@Composable
fun ModernChatScreen(
    userName: String = "",
    onBackPress: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val viewModel: ChatMessageVM = hiltViewModel()
    val uiState = viewModel.state.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .background(color = ColorFFE4E1)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(top = 12.dp)
        ) {
            TopAppBarMessage(
                userStatus = if (true) UserStatus.ONLINE else UserStatus.OFFLINE,
                onBackPressed = onBackPress,
                onCallPressed = {}
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            ChattingComponent(
                uiState.value.chats,
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            MessageInput(
                messageText = uiState.value.userInput ?: "",
                onMessageChange = { viewModel.onMessageChange(it) },
                onMessageSent = {
//                    keyboardController?.hide()
                    viewModel.onSendMessage()
                    viewModel.onMessageChange("")
                },
                ableType = true
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ModernChatScreenPreview() {
    ModernChatScreen("John Doe", {})
}