package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
private fun MakeupArtistItemPreview() {
    MakeupArtistItem()
}

@Composable
fun MakeupArtistItem() {
    return Box(
        modifier = Modifier.wrapContentSize()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

        }
    }
}