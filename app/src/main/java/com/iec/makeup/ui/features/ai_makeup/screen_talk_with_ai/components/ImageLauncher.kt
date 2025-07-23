package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iec.makeup.R


@Composable
fun ImageLauncher(
    camera: () -> Unit = {},
    gallery: () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, content = {
            Column(
                modifier = Modifier
                    .clickable { gallery() }
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.gallery),
                    contentDescription = "mobile",
                    modifier = Modifier
                        .size(36.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Gallery",
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier
                    .clickable { camera() }
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.mobile),
                    contentDescription = "mobile",
                    modifier = Modifier
                        .size(36.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Camera",
                )
            }


        }
    )
}