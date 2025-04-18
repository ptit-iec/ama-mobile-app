package com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.iec.makeup.R
import com.iec.makeup.core.model.Message
import com.iec.makeup.ui.features.ai_makeup.screen_response_ai.FeatureSelectorRow
import com.iec.makeup.ui.features.ai_makeup.screen_response_ai.InteractionScreen
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageBubble
import com.iec.makeup.ui.features.home.screen_chatting.box_chat_message.MessageInput
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFE4E1


@Composable
fun ScreenChatWithAI(
    navBack: () -> Unit = {},
    initialImageUrl: String = "",
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
            Box(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            horizontal = 12.dp
                        )
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = ColorDB7093,
                            spotColor = ColorDB7093
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorDB7093,
                    ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.funny_jake_adventure_time_cute_yellow_desktop_wallpaper_4k),
                        contentDescription = "j",
                        contentScale = ContentScale.FillHeight
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(
                            horizontal = 12.dp, vertical = 8.dp
                        ), // Add overall padding if desired
                    horizontalArrangement = Arrangement.Center, // Space between items
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularIconContainer(R.drawable.lips)
                    Spacer(modifier = Modifier.size(32.dp))
                    CircularIconContainer(R.drawable.contact_lens)
                    Spacer(modifier = Modifier.size(32.dp))
                    CircularIconContainer(R.drawable.make)
                }
            }

            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),

            ) {
                val (chatting, input) = createRefs()
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp).constrainAs(chatting){
                        bottom.linkTo(input.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ){
                    ChatWithAIComponents(
                        messages = listOf(
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                            Message(isFromUser = true, message = "Hello", timestamp = 0),
                            Message(isFromUser = false, message = "Hello", timestamp = 0),
                        )
                    )
                }
                Box(
                    modifier = Modifier.constrainAs(input){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ){
                    MessageInput(
                        messageText = "",
                        onMessageChange = {},
                        onMessageSent = {}

                    )
                }
            }
        }
    }
}

@Composable
fun CircularIconContainer(
    image: Int
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(CircleShape)// Clip the Box to a circle shape
            .background(Color.White), // Set the white background
        contentAlignment = Alignment.Center // Center the content within the Box
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(image),
            contentDescription = ""
        )
    }
}

@Composable
fun ChatWithAIComponents(
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
            .fillMaxWidth()
            .height(320.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .imePadding(),
            reverseLayout = true
        ) {

            itemsIndexed(
                messages.reversed()
            ) { index, chatMessage ->
                MessageBubble(
                    (index == 0) || (index > 0 && messages[index - 1].isFromUser),
                    chatMessage,
                    avatar = R.drawable.ai_technology
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    ScreenChatWithAI()
}