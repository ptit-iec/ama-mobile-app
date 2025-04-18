package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.R
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFE4E1
import com.iec.makeup.ui.theme.ColorFFF0F5
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class CardItemData(
    val id: Int, // Unique ID for stable list rendering
    val name: String,
    val description: String,
    val dateTime: String,
    val imageSource: Int // Placeholder for image source (URL, Drawable Res ID, Painter)
)


@Composable
fun InfoCard(
    item: CardItemData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp) // Adjust card width as needed
            .height(120.dp), // Adjust card height as needed
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = ColorFFF0F5
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Column for Text
            Column(
                modifier = Modifier
                    .weight(1f) // Takes available space left by the image
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween // Distribute space
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Allow up to 2 lines for description
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f)) // Push date/time to bottom
                Text(
                    text = item.dateTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            // Right Area for Image
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp) // Adjust image width as needed
                    .clip(
                        RoundedCornerShape(
                            topEnd = 16.dp,
                            bottomEnd = 16.dp
                        )
                    ) // Clip image to card corners
                    .background(Color.LightGray), // Placeholder background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.imageSource),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun AutoScrollingHorizontalCardList(
    items: List<CardItemData>,
    modifier: Modifier = Modifier,
    autoScrollDurationMillis: Long = 3000L // Time each item is visible
) {
    val lazyListState = rememberLazyListState()
    val itemCount = items.size

    // LaunchedEffect for auto-scrolling
    LaunchedEffect(key1 = itemCount) { // Relaunch if item count changes
        if (itemCount > 1) { // Only scroll if there's more than one item
            while (isActive) { // Loop while the coroutine is active
                delay(autoScrollDurationMillis) // Wait for the specified duration

                val currentFirstVisibleIndex = lazyListState.firstVisibleItemIndex
                val currentFirstVisibleOffset = lazyListState.firstVisibleItemScrollOffset

                // Calculate the next index, wrapping around if necessary
                var nextIndex = (currentFirstVisibleIndex + 1) % itemCount
                if (nextIndex == itemCount - 1) nextIndex = 0
                // If the first item is partially visible, scroll fully to it first,
                // then scroll to the next one. Otherwise, just scroll to the next.
                if (currentFirstVisibleOffset > 0) {
                    // Smoothly scroll to align the current item fully
                    lazyListState.animateScrollToItem(
                        index = currentFirstVisibleIndex,
                        scrollOffset = 0
                    )
                    delay(autoScrollDurationMillis / 4) // Short pause after alignment
                    // Scroll to the actual next item
                    lazyListState.animateScrollToItem(index = nextIndex)
                } else {
                    // Scroll directly to the next item
                    lazyListState.animateScrollToItem(index = nextIndex)
                }

            }
        }
    }

    LazyRow(
        state = lazyListState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp), // Padding around the list
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Space between cards
    ) {
        items(
            items = items,
            key = { item -> item.id } // Use unique ID for stable recomposition
        ) { item ->
            InfoCard(item = item)
        }
    }
}

fun getSampleCardData(): List<CardItemData> {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return List(10) { index ->
        CardItemData(
            id = index,
            name = "Tutorial $index",
            description = "This tutorial covers the basics of Make up Session $index",
            dateTime = dateFormat.format(Date()),
            imageSource = R.drawable.pick1 // Replace with R.drawable.your_image or URL String later
            // Example with drawable: imageSource = R.drawable.placeholder_image
        )
    }
}

@Composable
fun AutoScrollScreen() {
    val sampleData = remember { getSampleCardData() }

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        AutoScrollingHorizontalCardList(items = sampleData)
        // Add other content below if needed
    }
}

@Preview(showBackground = true)
@Composable
fun AutoScrollScreenPreview() {
    AutoScrollScreen()
}