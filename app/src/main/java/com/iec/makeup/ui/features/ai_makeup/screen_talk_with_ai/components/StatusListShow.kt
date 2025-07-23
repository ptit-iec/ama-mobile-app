package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.iec.makeup.R


@Composable
fun StatusListShow(
    statusList: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
    modifier: Modifier
){
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(Unit) {

    }
    LazyColumn(
        modifier = modifier,
        state = lazyColumnState
    ) {
        items(statusList.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ){
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "check"
                )
                Text(
                    text = statusList[index],
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}