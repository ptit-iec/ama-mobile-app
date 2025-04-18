package com.iec.makeup.ui.features.home.screen_chatting.box_chat_message

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.ui.feature.main.message.convertTimeStamp
import com.iec.makeup.R
import com.iec.makeup.core.model.Message
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFE4E1


@Composable
fun ChattingComponent(
    messages: List<Message>,
) {

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.scrollToItem(messages.size - 1)
    }
    LaunchedEffect(key1 = messages.lastOrNull()?.message) {
        Log.d("ChatMessage", if (messages.isNotEmpty()) messages.last().message else "No")
    }
    Box(
        modifier = Modifier

            .background(ColorFFE4E1)
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .imePadding()
                .fillMaxSize(),
            reverseLayout = true
        ) {

            itemsIndexed(
                messages.reversed()) { index, chatMessage ->
                MessageBubble(
                    (index == 0) || (index > 0 && messages[index - 1].isFromUser),
                    chatMessage
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageInput(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onMessageSent: (String) -> Unit,
    ableType: Boolean = true,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ai_technology),
                contentDescription = ""
            )
        }
        TextField(
            value = messageText,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 4.dp),
            placeholder = {
                Text("Bạn muốn chỉnh sửa gì?", color = Color.DarkGray, fontSize = 12.sp)

            },
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(18.dp),
            maxLines = Int.MAX_VALUE
        )

        Image(
            imageVector = Icons.AutoMirrored.Filled.Send,
            colorFilter = if (ableType) ColorFilter.tint(ColorDB7093) else ColorFilter.tint(
                Color.Black
            ),
            contentDescription = "Send",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    if (messageText.isNotBlank()) {
                        onMessageSent(messageText)
                    }
                }
        )
    }
}

@Composable
fun MessageBubble(
    showAvatar: Boolean,
    message: Message,
    avatar: Int = R.drawable.account_circle_24dp_df9d9b_fill1_wght400_grad0_opsz24
) {
    var onShowTimeStamp by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
//            if (!message.isFromUser) {
//                Image(
//                    modifier = Modifier
//                        .padding(end = 6.dp)
//                        .alpha(if (showAvatar) 1f else 0f)
//                        .background(color = Color.Transparent, shape = CircleShape)
//                        .size(30.dp),
//                    painter = painterResource(avatar),
//                    contentDescription = "Profile Picture",
//                )
//            }
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = if (message.isFromUser) 20.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 20.dp
                ),
                color = ColorDB7093
            ) {

                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable(
                            interactionSource,
                            indication = null
                        ) {
                            onShowTimeStamp = !onShowTimeStamp
                        }

                ) {
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = message.message,
                        fontSize = 12.sp,
                        color = ColorFFE4E1

                    )
                    if (onShowTimeStamp) {
                        Text(
                            text = convertTimeStamp(System.currentTimeMillis() - message.timestamp),
                            fontSize = 10.sp,
                            color = if (message.isFromUser)
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }

        }

    }
}

enum class UserStatus {
    ONLINE, OFFLINE
}

@Composable
fun TopAppBarMessage(
    userStatus: UserStatus = UserStatus.ONLINE,
    onBackPressed: () -> Unit,
    onCallPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = ColorFFE4E1
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            colorFilter = ColorFilter.tint(ColorDB7093),
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onBackPressed()
                },
            contentDescription = "Back"
        )
        // Profile Picture
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color = Color.Transparent)
        ) {
            Image(
                painter = painterResource(R.drawable.account_circle_24dp_df9d9b_fill1_wght400_grad0_opsz24),
                contentDescription = ""
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Gemini",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
            )
            when (userStatus) {
                UserStatus.ONLINE -> {
                    Text(
                        "Online",
                        fontSize = 12.sp,
                        color = Color.Green
                    )
                }

                UserStatus.OFFLINE -> {
                    Text(
                        "Offline",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
        }

        Icon(
            imageVector = Icons.Default.Call,
            tint = ColorDB7093,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp)
                .clickable {
                    onCallPressed()
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUI() {
    MessageBubble(
        true,
        Message(
            isFromUser = false,
            message = "Helsdafsdjfioasjdoifjaoisasdfasdfiojasodijfosiddfjaoisdjfoajdsoifjdosalo",
            timestamp = 0
        )
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewUI1() {
    TopAppBarMessage(
        userStatus = UserStatus.OFFLINE,
        onBackPressed = {},
        onCallPressed = {})

}

@Preview(showBackground = true)
@Composable
fun PreviewUI3() {
    MessageInput(
        messageText = "",
        onMessageChange = {},
        onMessageSent = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUI2() {
    ChattingComponent(
        listOf(
            Message(isFromUser = true, message = "Hello", timestamp = 0),
            Message(isFromUser = false, message = "Hello", timestamp = 0),
            Message(isFromUser = true, message = "Hello", timestamp = 0),
            Message(isFromUser = false, message = "Hello", timestamp = 0),
        )
    )

}

