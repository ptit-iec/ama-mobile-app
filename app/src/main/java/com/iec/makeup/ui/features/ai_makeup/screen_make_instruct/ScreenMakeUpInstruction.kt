package com.iec.makeup.ui.features.ai_makeup.screen_make_instruct

import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAIEffect
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAIEvent
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAIVM
import com.iec.makeup.ui.theme.ColorDB7093
import dev.jeziellago.compose.markdowntext.MarkdownText


@Composable
fun ScreenMakeUpInstruction(
    navBack: () -> Unit = {},
    conversationID: String = ""
) {
    val viewModel: ScreenChatWithAIVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(null)
    LaunchedEffect(Unit) {
        Log.d("ViewModel hash", viewModel.hashCode().toString())
        viewModel.getInstruction(conversationID)
    }
    effect.value?.let {
        if ( it is ScreenChatWithAIEffect.OnErrorEffect && it.message != null) {
            DialogCompose(
                text = it.message,
                onCloseAction = viewModel::hideErrorMessage,
                positiveAction = viewModel::hideErrorMessage
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = ColorDB7093,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .clickable {
                        navBack()
                    }
            )
            Text(
                text = "Hướng dẫn chi tiết",
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
        MarkdownText(
            markdown = state.value.instruction ?: "Chưa có hướng dẫn chi tiết",
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
            ),
        )
    }

}
