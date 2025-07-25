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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFE4E1
import com.iec.makeup.ui.theme.ColorFFF0F5
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AutoScrollingHorizontalCardList(
    items: List<Int>,
    modifier: Modifier = Modifier,
    autoScrollDurationMillis: Long = 3000L
) {
    val lazyListState = rememberLazyListState()
    val itemCount = items.size
    LaunchedEffect(key1 = Unit) {
        if (itemCount > 1) {
            while (isActive) {
                delay(autoScrollDurationMillis)

                val currentFirstVisibleIndex = lazyListState.firstVisibleItemIndex
                val currentFirstVisibleOffset = lazyListState.firstVisibleItemScrollOffset

                var nextIndex = (currentFirstVisibleIndex + 1) % itemCount
                if (nextIndex == itemCount - 1) nextIndex = 0
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

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LazyRow(
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->

            Card(
                modifier = Modifier
                    .width(screenWidth*0.9f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Image(
                    painter = painterResource(id = item),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 180.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAutoScrollingHorizontalCardList() {
    AutoScrollingHorizontalCardList(items = getSampleCardData())
}
fun getSampleCardData(): List<Int> {
    return listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4
    )
}
