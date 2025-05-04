package com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IntegrationInstructions
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.model.HEADER
import com.iec.makeup.core.model.Message
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageBubble
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageInput
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun ScreenChatWithAI(
    navBack: () -> Unit = {},
    chatBotID: String = "",
    imageLink: String = "https://pub-bea49f62a5e5402da48a734a9e29f52d.r2.dev/makeup_results/makeup_style_36506ab4-8b52-4340-b972-175937bee3ca.jpg",
    navHome: () -> Unit = {},
    navToInstruction: () -> Unit = {},
    navToExpertsRcm: (String) -> Unit = {},
    viewModel: ScreenChatWithAIVM
) {

    var imageFullView by remember { mutableStateOf<String?>(null) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val appState = LocalAppState.current
    LaunchedEffect(Unit) {
        if (chatBotID.isEmpty()) {
            navBack()
        } else {
            if(state.value.messages.isEmpty()){
                viewModel.sendEvent(
                    ScreenChatWithAIEvent.OnSentMessage(
                        Message(
                            message = imageLink,
                            isFromUser = true,
                            header = HEADER.IMAGE,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                )
            }
            viewModel.getConversationID(chatBotID)
        }
    }
    LaunchedEffect(Unit) {
        Log.d("ViewModel hashed", viewModel.hashCode().toString())
    }
    ScreenChatStateless(
        navBack = navBack,
        state = state.value,
        navHome = navHome,
        navToInstruction = navToInstruction,
        navToExpertsRcm = navToExpertsRcm,
        onMessageChange = viewModel::onUserInput,
        onMessageSent = viewModel::onUserSentMessage,
        onInitMessageSent = { viewModel.onUserSentMessage(imageLink) },
        onViewImage = {
            imageFullView = it
        }
    )
    appState.setLoading(state.value.isLoading)
    effect.value?.let {
        when(it){
            is ScreenChatWithAIEffect.OnErrorEffect -> {
                if(it.message != null){
                    DialogCompose(
                        text = it.message ?: "Oops! Something went wrong",
                        onCloseAction = {
                            viewModel.sendEventWithEffect(ScreenChatWithAIEvent.OnError(null))
                        }
                    )
                }
            }

            ScreenChatWithAIEffect.DismissImageView -> {}
            is ScreenChatWithAIEffect.ShowImageView -> {}
        }
    }
}


@Composable
fun ScreenChatStateless(
    navBack: () -> Unit = {},
    state: ScreenChatWithAIState = ScreenChatWithAIState(),
    navHome: () -> Unit = {},
    navToInstruction: () -> Unit = {},
    navToExpertsRcm: (String) -> Unit = {},
    onMessageChange: (String) -> Unit = {},
    onMessageSent: () -> Unit = {},
    onInitMessageSent: () -> Unit = {},
    onViewImage: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    start = 8.dp, end = 8.dp, top = 8.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navBack()
                        }
                )

                Text(
                    text = "TRỢ LÝ MAKEUP",
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                ColorDB7093,
                                Color.LightGray
                            )
                        )
                    )
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 12.dp)
                    .weight(1f)
            ) {
                ChatWithAIComponents(
                    messages = state.messages,
                    imageView = onViewImage
                )
                if (state.isBotTyping) {
                    Text(
                        text = "BOT is typing ...",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    NavigationItem(
                        icon = Icons.Default.Home,
                        text = "Về trang chủ"
                    ) {
                        navHome()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    NavigationItem(
                        icon = Icons.Default.IntegrationInstructions,
                        text = "Xem hướng dẫn"
                    ) {
                        navToInstruction()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    NavigationItem(
                        icon = Icons.Default.Search,
                        text = "Tìm chuyên gia"
                    ) {
                        navToExpertsRcm("questionSessionID")
                    }
                }
            }
            MessageInput(
                messageText = state.inputChat ?: "",
                onMessageChange = {
                    onMessageChange(it)
                },
                onMessageSent = {
//                    onInitMessageSent()
                    if (state.messages.size < 2) {
                        onInitMessageSent()
                    } else {
                        onMessageSent()
                    }
                }
            )
        }
    }
}

@Composable
fun ChatWithAIComponents(
    messages: List<Message>,
    imageView: (String) -> Unit = {}
) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
       if(messages.isNotEmpty()){
           listState.scrollToItem(messages.size - 1)
       }
    }
    LaunchedEffect(key1 = messages.lastOrNull()?.message) {
        Log.d("ChatMessage", if (messages.isNotEmpty()) messages.last().message else "No")
    }
    Box(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
    ) {
        LazyColumn(
            modifier = Modifier,
            state = listState
        ) {
            itemsIndexed(
                messages
            ) { index, chatMessage ->
                MessageBubble(
                    false,
                    chatMessage,
                    avatar = R.drawable.ai_technology,
                    imageView = imageView,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp) // Add some padding around each item
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp) // Size of the circular border
                .clip(CircleShape)
                .border(BorderStroke(1.dp, ColorDB7093), CircleShape) // Red border
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null, // Or provide a meaningful content description
                modifier = Modifier.size(18.dp), // Size of the icon
                tint = Color.Black // Adjust icon color if needed
            )
        }
        Spacer(modifier = Modifier.height(4.dp)) // Space between icon and text
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black // Adjust text color if needed
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    ScreenChatStateless()
}