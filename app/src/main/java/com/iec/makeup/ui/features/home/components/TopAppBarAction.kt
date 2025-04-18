package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Preview
@Composable
private fun TopPreview() {
    TopAppBar(
        showSearch = {},
        showNotifications = {},
        showChat = {},
        image = ""
    )
}

@Composable
fun TopAppBar(
    showSearch: () -> Unit = {},
    showNotifications: () -> Unit = {},
    showChat: () -> Unit = {},
    image: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier.size(36.dp),
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
            if (DateTimeUtils.getCurrentDateTime().isMorning()) {
                Text(
                    text = "Good morning",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(
                        Font(R.font.montserrat)
                    ),
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.White
                )
            } else {
                Text(
                    text = "Good noon",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(
                        Font(R.font.montserrat)
                    ),
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.White
                )
            }
        }
        // Right Icons
        Row {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Compare",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        showSearch()
                    },
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = "Notifications",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        showNotifications()
                    },
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.Chat,
                contentDescription = "Settings",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        showChat()
                    },
                tint = Color.White
            )
        }
    }
}