package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.utils.DateTimeUtils
import com.iec.makeup.core.utils.DateTimeUtils.isMorning
import com.iec.makeup.ui.theme.primaryColor

@Preview
@Composable
private fun TopPreview() {
    TopAppBar(
        showNotifications = {},
        image = ""
    )
}

@Composable
fun TopAppBar(
    showNotifications: () -> Unit = {},
    image: String,
    name: String = "Ngo Tuan Anh",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .background(
                color = Color(0xFFFFF3E9),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Card(
                modifier = Modifier.size(28.dp),
                shape = CircleShape,
                elevation = CardDefaults.elevatedCardElevation(0.dp),
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "Logo",
                    contentScale = ContentScale.FillHeight
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.montserrat)
                ),
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(8.dp))

        }
        // Right Icons
        Icon(
            painter = painterResource(R.drawable.bell_01),
            contentDescription = "Notifications",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(24.dp)
                .clickable {
                    showNotifications()
                },
            tint = Color.White
        )
    }
}