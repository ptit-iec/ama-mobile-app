package com.iec.makeup.ui.features.home.screen_notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun NotificationContent(
    navBack: () -> Unit = {}
) {
    val viewModel: NotificationVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navBack()
                    }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "6 new",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(viewModel.state.value.notifications.size) { index ->
                    NotificationItem(
                        icon = Icons.Default.Notifications,
                        title = state.value.notifications[index].title,
                        content = state.value.notifications[index].message,
                        iconBackgroundColor = Color.White
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
    if(state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AtomicLoadingDialog()
        }
    }

}

@Composable
fun NotificationItem(
    icon: Any,
    title: String,
    content: String,
    iconBackgroundColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icon with background
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            when (icon) {
                is androidx.compose.ui.graphics.vector.ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = ColorDB7093,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.width(4.dp))

                // Green dot for new notifications
                if (title != "Cart Reminders") {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CD964))
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Right arrow
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFE91E63)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    MaterialTheme {
        NotificationContent()
    }
}