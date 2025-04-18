package com.iec.makeup.ui.features.ai_makeup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R


@Composable
fun ManualScreenComponents(
    imageInstruction: Int,
    textInstruction: String,
    detailInstruction: String
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(imageInstruction),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = textInstruction,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.padding(horizontal = 32.dp)
            ){
                Text(
                    text = detailInstruction,
                    style = TextStyle(
                        fontSize = 16.sp,
                    ),
                    textAlign = TextAlign.Center)
            }


        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun Preview() {
    ManualScreenComponents(
        imageInstruction = R.drawable.instruction_1_2,
        textInstruction = "Prioritize \nContent",
        detailInstruction = "Dùng ánh sáng tự nhiên hoặc dàn chiếu đều, không để bóng tối che mất.\n" +
                "Tránh ánh sáng quá mạnh gây lóa hoặc đổ bóng."
    )
}