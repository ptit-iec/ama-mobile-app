package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.R
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.ControlComponent
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.LanguageBottomBar
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.MessageInputBox
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.ProcessThinkingComponent
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.StatusListShow
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components.VerifiedTextField
import kotlin.math.log

// Version 2.0

@Composable
fun ScreenTalkWithAI(
    navBack: () -> Unit = {},
    viewModel: ScreenTalkVM = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(null)
    var isBoxChatOpen by remember { mutableStateOf(false) }
    var isCancelCallDialog by remember { mutableStateOf(false) }
    var context = LocalContext.current

    var isShowBottomLanguage by remember { mutableStateOf(true) }

    LaunchedEffect(
        key1 = effect.value,
    ) {
        effect.value?.let {
            when (it) {
                is ScreenTalkViewEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(
        )
    ) {
        ScreenTalkWithAIStateless(
            onOpenChat = {
                isBoxChatOpen = !isBoxChatOpen
            },
            onCancelCall = {
                isCancelCallDialog = true
            },
            onReceiveText = {
                viewModel.onUserInput(it)
            },
            aiThinking = state.value.thinkingMode,
            aiStatus = state.value.statusAI,
        )


        if (state.value.modifyBoxState) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 180.dp
                    )
            ) {
                VerifiedTextField(
                    modifyChange = {
                        viewModel.onUserConfirm(it)
                    },
                    state.value.userSpokenText.last(),
                    onCancel = {
                        viewModel.onUserCancel()
                    }
                )
            }
        }
    }

    if (isCancelCallDialog) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DialogCompose(
                text = "Do you want to cancel the call?",
                negativeAction = {
                    isCancelCallDialog = !isCancelCallDialog
                },
                positiveAction = {
                    navBack()
                },
                ableDismiss = true,
                onCloseAction = {
                    isCancelCallDialog = !isCancelCallDialog
                }
            )
        }
    }
    if (isBoxChatOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFF000000).copy(alpha = 0.5f)
                )
                .clickable {
                    isBoxChatOpen = false
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            MessageInputBox(
                onClose = {
                    isBoxChatOpen = false
                },
                onSend = {
                    viewModel.onUserConfirm(it)
                    isBoxChatOpen = false
                }
            )
        }
    }
    
    if(isShowBottomLanguage){
        LanguageBottomBar(
            onDismissRequest = {
                isShowBottomLanguage = false
            },
            onSelectLang = {
//                viewModel.onSelectLanguage(it)
                isShowBottomLanguage = false
            }
        )
    }

}


@Composable
fun ScreenTalkWithAIStateless(
    onOpenChat: () -> Unit = {},
    onCancelCall: () -> Unit = {},
    onReceiveText: (String) -> Unit = {},
    aiThinking: String? = null,
    aiStatus: List<String> = listOf()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFDCDF),
                        Color.White
                    )
                )
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            // Create references for the composables to constrain
            val (title, settingsIcon, logo, controls, thinking, status) = createRefs()

            // Create a vertical chain to replicate Arrangement.SpaceBetween
            createVerticalChain(title, logo, controls, chainStyle = ChainStyle.SpreadInside)

            // Title Text
            Text(
                text = "Makeup AI",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5969),
                modifier = Modifier.constrainAs(title) {
                    // Center horizontally to the parent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    // Top position is handled by the chain
                }.padding(top = 16.dp)
            )

            // Settings Icon
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.DarkGray,
                modifier = Modifier.constrainAs(settingsIcon) {
                    // Align to the end of the parent
                    end.linkTo(parent.end, margin = 8.dp)
                    // Center vertically with the title
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                }.padding(top = 16.dp)
            )

            aiThinking?.let {
                Box(
                    modifier = Modifier.constrainAs(thinking) {
                        // Center horizontally to the parent
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom)
                        bottom.linkTo(logo.top)
                        // Vertical position is handled by the chain
                    }
                ){
                    ProcessThinkingComponent(
                        message = it
                    )
                }
            }
            // Logo Image
            Image(
                painter = painterResource(R.drawable.group_270), // Replace with your drawable
                contentDescription = "Logo",
                modifier = Modifier.constrainAs(logo) {
                    // Center horizontally to the parent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    // Vertical position is handled by the chain
                }
            )


            if(aiStatus.isNotEmpty()) {
                Box(
                    modifier = Modifier.constrainAs(status){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(logo.bottom)
                        bottom.linkTo(controls.top)
                    }
                ){
                    StatusListShow(
                        statusList = aiStatus,
                        modifier = Modifier
                            .heightIn(max = 200.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                    )
                }
            }

            // Control Component
            Box(
                modifier = Modifier.constrainAs(controls) {
                    // Center horizontally to the parent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    // Bottom position is handled by the chain
                }
            ) {
                ControlComponent(
                    onOpenChat = onOpenChat,
                    onCancelCall = onCancelCall,
                    onReceiveText = onReceiveText,
                    )
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreenTalkWithAI() {
    Box(
        modifier = Modifier.fillMaxSize(
        )
    ) {
        ScreenTalkWithAIStateless(
        )
    }
}