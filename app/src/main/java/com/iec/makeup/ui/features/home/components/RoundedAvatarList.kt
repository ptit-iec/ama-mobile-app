package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.iec.makeup.core.model.User
import kotlin.math.truncate

// --- Instagram-like Gradient Brush ---
val instagramStoryBrush = Brush.sweepGradient(
    listOf(
        Color(0xFFC913B9), // Purple
        Color(0xFFF9373F), // Red
        Color(0xFFFECD00)  // Yellow
    )
)

// --- Composable for a Single Avatar ---
@Composable
fun UserStoryAvatar(
    user: User,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 50.dp,
    borderWidth: Dp = 2.dp,
    borderPadding: Dp = 3.dp // Space between border and image
) {
    // Total size includes avatar + padding + border width on both sides
    val totalSize = avatarSize + (borderPadding + borderWidth) * 2
    val imageSize = avatarSize

    val borderModifier = if (true) {
        Modifier
            .border(
                BorderStroke(borderWidth, instagramStoryBrush),
                CircleShape
            )
            .padding(borderPadding) // Apply padding *inside* the border
    } else {
        Modifier // No border if no story
    }

    Column(
        modifier = modifier.width(totalSize), // Give column a fixed width for text centering
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(totalSize) // Box contains border and image
                .then(borderModifier) // Apply border modifier if needed
                .padding(borderWidth) // Padding accounts for border thickness itself
                .clip(CircleShape), // Clip the outer box to ensure border padding works correctly
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://yt3.googleusercontent.com/taR8GgX9B2d6YGDpNe27NBXrsTQkG2KGYwnzILYJFHdCBxeP-TnJBBpiAzDQ9-jC1AR7qteQ=s900-c-k-c0x00ffffff-no-rj")
                    .crossfade(true)
                    .build(),
                contentDescription = "${user.name}'s profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape) // Clip the image itself to a circle
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = user.name ?: "",
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(totalSize) // Ensure text uses the available width
        )
    }
}

// --- Composable for the Horizontal List ---
@Composable
fun FollowerStoryList(
    users: List<User>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp), // Padding at the start/end of the list
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Spacing between avatars
    ) {
        items(users) { user ->
            UserStoryAvatar(user = user)
        }
    }
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun FollowerStoryListPreview() {
    val sampleUsers = listOf(
        User("", "user1", "zzmitzz"),
        User("", "user2", "zxmitzz"),
        User("", "user3", "Lmao")
    )
    FollowerStoryList(users = sampleUsers)
}
