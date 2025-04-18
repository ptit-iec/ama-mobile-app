package com.iec.makeup.ui.features.home.screen_search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.core.ui.IECTextField
import com.iec.makeup.ui.theme.ColorDB7093
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    navBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val tabs = listOf("People", "Orders", "Reels")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        // Top app bar with search
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
            ),
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
            SearchTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onClearClick = { searchQuery = "" }
            )
        }

        // Tab Row for filters
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            contentColor = ColorDB7093,
            edgePadding = 8.dp,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) }
                )
            }
        }

        // Content pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            SearchResultsContent(
                category = tabs[page],
                searchQuery = searchQuery
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
            .background(Color.LightGray.copy(alpha = 0.8f))
    ) {
        Card(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IECTextField(
                    placeholder = "Search ...",
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .height(40.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SearchResultsContent(
    category: String,
    searchQuery: String
) {
    // For demo purposes, create some mock data based on category
    val items = remember(category, searchQuery) {
        when (category) {
            "All" -> getMockItems(20, "Item", searchQuery)
            "Photos" -> getMockItems(15, "Photo", searchQuery)
            "Videos" -> getMockItems(10, "Video", searchQuery)
            "Documents" -> getMockItems(12, "Document", searchQuery)
            "Music" -> getMockItems(8, "Track", searchQuery)
            else -> emptyList()
        }
    }

    if (items.isEmpty()) {
        EmptySearchResults(category)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                SearchResultItem(item = item)
            }
        }
    }
}

@Composable
fun SearchResultItem(item: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // You could add an icon based on the item type here
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun EmptySearchResults(category: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No $category Found",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Try a different search term or category",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Helper function to create mock data
private fun getMockItems(count: Int, prefix: String, query: String): List<String> {
    return if (query.isEmpty()) {
        List(count) { "$prefix ${it + 1}" }
    } else {
        List(count) { "$prefix ${it + 1}" }
            .filter { it.contains(query, ignoreCase = true) }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    SearchScreen()
}