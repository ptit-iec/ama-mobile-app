package com.iec.makeup.ui.features.home.screen_all_makeup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.remote.dto.Rating
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.screen_all_makeup.components.MakeUpItemCard
import com.iec.makeup.ui.features.home.screen_all_makeup.components.SearchBar


@Composable
fun AllMakeUpScreen(
    navBack: () -> Unit = {},
    navToDetail: (String) -> Unit = {}
) {
    val viewModel: AllMakeUpVM = hiltViewModel()
    val context = LocalContext.current
    val appState = LocalAppState.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)


    AllMakeUpScreenStateless(
        navBack = navBack,
        navToDetail = navToDetail,
        data = state.value.data,
        loadMore = viewModel::loadMoreExperts
    )

    appState.setLoading(state.value.isLoading)
}

@Composable
fun AllMakeUpScreenStateless(
    navBack: () -> Unit = {},
    navToDetail: (String) -> Unit = {},
    data: List<Expert> = emptyList(),
    loadMore: () -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val remainItemForLoadMore = 3
    LaunchedEffect(scrollState.isScrollInProgress) {
        snapshotFlow {
            scrollState.layoutInfo
        }.collect {
            if (it.totalItemsCount > 0 && (it.visibleItemsInfo.lastOrNull()?.index
                    ?: 0) >= it.totalItemsCount - remainItemForLoadMore
            ) {
                loadMore()
            }
        }
    }
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
            LazyColumn(
                state = scrollState,
            ) {
                items(data.size) { index ->
                    val item = data[index]
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
    AllMakeUpScreenStateless(
        data = listOf(
            Expert(
                name = "Dr. Arlene McCoy",
                avatar = "https://i.ytimg.com/vi/DYkKy3FtSv8/maxresdefault.jpg",
                description = "Chưa cập nhật",
                rating = Rating(
                    count = 5,
                    average = 4.5,
                    display = "4.5"
                )
            )
        )
    )
}

