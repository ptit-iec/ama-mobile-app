package com.iec.makeup.ui.features.home.screen_all_makeup.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.core.ui.IECTextField
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun SearchBar(
    callbackSearchQuery: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val searchQuery = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .padding(8.dp)
                .size(22.dp)
                .clickable {
                    onBackClick()
                }
        )
        Card(
            modifier = Modifier
                .height(40.dp)
                .padding(end = 12.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, ColorDB7093)
        ) {
            IECTextField(
                placeholder = "Search ...",
                value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    callbackSearchQuery(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar()
}