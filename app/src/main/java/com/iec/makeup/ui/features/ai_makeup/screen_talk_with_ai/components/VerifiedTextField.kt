package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.core.ui.IECTextField
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.primaryColor


@Composable
fun VerifiedTextField(
    modifyChange: (String) -> Unit,
    initValue: String,
    onCancel: () -> Unit
) {

    var currentTextValue by remember { mutableStateOf<String>(initValue) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ColorDB7093),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)) {
            IECTextField(
                placeholder = "",
                value = currentTextValue,
                onValueChange = {
                    currentTextValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.clickable {
                        onCancel()
                    }.padding(end = 12.dp)
                )
                Text(
                    text = "Send",
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = primaryColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .clickable {
                            modifyChange(currentTextValue)
                        },
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVerifiedTextField() {
    VerifiedTextField(
        {},"", {}
    )
}