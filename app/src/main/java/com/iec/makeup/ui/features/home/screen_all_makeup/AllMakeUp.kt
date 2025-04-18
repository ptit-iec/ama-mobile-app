package com.iec.makeup.ui.features.home.screen_all_makeup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.core.model.ui.mockListData
import com.iec.makeup.ui.features.home.screen_all_makeup.components.MakeUpItemCard
import com.iec.makeup.ui.features.home.screen_all_makeup.components.SearchBar


@Composable
fun AllMakeUpScreen(
    navBack: () -> Unit = {},
    navToDetail: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            SearchBar(
                onBackClick = navBack
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            val fakeData = mockListData
            LazyColumn {
                items(fakeData.size) { index ->
                    val item = fakeData[index]
                    MakeUpItemCard(
                        item = item,
                        onBookNowClick = { /* Handle book now click */ },
                        onNavToDetail = navToDetail
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AllMakeUpScreen()
}

