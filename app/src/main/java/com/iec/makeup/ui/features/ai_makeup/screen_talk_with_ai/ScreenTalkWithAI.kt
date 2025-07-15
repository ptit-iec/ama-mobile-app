package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R

// Version 2.0

@Composable
fun ScreenTalkWithAI(){

}


@Composable
fun ScreenTalkWithAIStateless(){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFDCDF),
                        Color.White
                    )
                )
            )
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "GlamAura",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5969),
                modifier = Modifier.padding(top = 8.dp)
            )
            Image(
                painter = painterResource(R.drawable.group_270),
                contentDescription = "Logo"
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.chat),
                        contentDescription = "Chat"
                    )
                    Text(
                        text = "Open chat",
                    )
                }
                Image(
                    painter = painterResource(R.drawable.mi)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreenTalkWithAI(){
    ScreenTalkWithAIStateless()
}