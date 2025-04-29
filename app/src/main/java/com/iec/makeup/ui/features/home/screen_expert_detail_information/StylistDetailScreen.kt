package com.iec.makeup.ui.features.home.screen_expert_detail_information

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.data.remote.dto.ExpertDetail
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.screen_expert_detail_information.ui_components.ItemReviewCard
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFAF9F9


enum class PagerTab(val title: String) {
    PROFILE("Hồ Sơ"),
    REVIEW("Đánh giá")
}

@Composable
fun ProfileScreen(
    navBack: () -> Unit = {},
    id: String = "Vu Hoai Nam"
) {


    val context = LocalContext.current
    val viewModel: StylistDetailScreenVM = hiltViewModel()

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val appState = LocalAppState.current

    LaunchedEffect(Unit) {
        viewModel.initData(id)
    }

    ProfileScreenStateless(
        navBack = navBack,
        state = state.value
    )
    appState.setLoading(state.value.isLoading)
}


@Composable
fun ProfileScreenStateless(
    navBack: () -> Unit = {},
    state: StylistDetailScreenState = StylistDetailScreenState(
        expertData = ExpertDetail(
            name = "Vu Hoai Nam",
            avatar = "https://i.ytimg.com/vi/DYkKy3FtSv8/maxresdefault.jpg",
            description = "Chưa cập nhật",
            address = "Chưa cập nhật",
        ),
        isLoading = false,
        error = null
    )
) {
    var pagerType by remember { mutableStateOf(PagerTab.PROFILE) }
    Scaffold(
        topBar = { ProfileTopAppBar(navBack) },
        containerColor = ColorFAF9F9
    ) { paddingValues ->
        state.expertData?.let {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Make the whole column scrollable
            ) {
                ProfileHeader(
                    avatar = state.expertData.avatar,
                    name = state.expertData.name ?: "Default",
                    address = state.expertData.address,
                    description = state.expertData.description

                )
                ActionButtons(
                    onActionClick = {}
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Make the row take the full width
                        .padding(4.dp), // Add some padding around the row
                    horizontalArrangement = Arrangement.SpaceEvenly, // Distribute space evenly between items
                    verticalAlignment = Alignment.CenterVertically // Vertically center the items
                ) {
                    // Profile Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            pagerType = PagerTab.PROFILE
                        }) {
                        Icon(
                            imageVector = Icons.Default.List, // Placeholder icon for "Hồ sơ"
                            contentDescription = "Profile Icon",
                            tint = if (pagerType == PagerTab.PROFILE) Color.Red else Color.Black, // Example orange color), // Example color
                            modifier = Modifier.size(24.dp) // Set icon size
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // Add space between icon and text
                        Text(
                            text = "Hồ sơ",
                            color = if (pagerType == PagerTab.PROFILE) Color.Red else Color.Black // Example color for text
                        )
                    }

                    // Vertical Separator
                    Divider(
                        color = Color.Black, // Color of the separator
                        modifier = Modifier
                            .height(24.dp) // Set the height of the separator
                            .width(1.dp) // Set the width of the separator
                    )

                    // Review Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            pagerType = PagerTab.REVIEW
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star, // Placeholder icon for "Đánh giá"
                            contentDescription = "Review Icon",
                            tint = Color(0xFFFF9800), // Example orange color for star
                            modifier = Modifier.size(24.dp) // Set icon size
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // Add space between icon and text
                        Text(
                            text = "Đánh giá",
                            color = if (pagerType == PagerTab.REVIEW) Color.Red else Color.Black
                        )
                    }
                }
                if (pagerType == PagerTab.PROFILE) {
                    if (state.expertData.samplesByCategory.isEmpty()) {
                        Text(
                            text = "Không có dữ liệu",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        state.expertData.samplesByCategory.forEach {
                            ImageSection(
                                title = it.title!!,
                                images = it.samples.map { it.image!! }) // Pass actual data
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                } else {
                    if (state.userReviews.isNullOrEmpty()) {
                        Text(
                            text = "Không có dữ liệu",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.weight(1f).padding(16.dp),
                        ) {
                            items(state.userReviews.size) { index ->
                                ItemReviewCard(
                                    item = state.userReviews[index],
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// --- Top App Bar ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(
    navBack: () -> Unit = {},
) {
    TopAppBar(
        title = { /* No title shown in the screenshot */ },
        navigationIcon = {
            IconButton(onClick = { navBack() }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle share action */ }) {
                Icon(Icons.Filled.Bookmark, contentDescription = "Share")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorFAF9F9, // Or Color.White
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// --- Profile Header Section ---
@Composable
fun ProfileHeader(
    avatar: String? = null,
    name: String = "Vu Hoai Nam",
    address: String? = null,
    description: String? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = avatar ?: "https://i.ytimg.com/vi/DYkKy3FtSv8/maxresdefault.jpg",
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray.copy(alpha = 0.5f), CircleShape) // Optional border
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = ColorDB7093,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = address ?: "Chưa cập nhật",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description ?: "Chưa cập nhật",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary // Or a specific blue color
        )
    }
}

// --- Profile Stats Section (Posts, Followers, Following) ---
@Composable
fun ProfileStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp), // Add horizontal padding
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround // Distribute space evenly
    ) {
        StatItem(count = "102", label = "Orders")
        StatItem(count = "1.5K", label = "Followers")
        RatingItem(count = "4.3/5 ⭐", label = "Rating")
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun RatingItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}


// --- Action Buttons (Book, Follow) ---
@Composable
fun ActionButtons(onActionClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center // Space between buttons
    ) {
        Button(
            onClick = { /* Handle Book action */ },
            modifier = Modifier.wrapContentWidth(), // Takes up half the available space
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)) // Pink color
        ) {
            Text(stringResource(R.string.book), color = Color.White)
        }
    }
}


// --- Reusable Image Section (Make up, Skin care) ---
@Composable
fun ImageSection(title: String, images: List<String>) { // Use List<String> for URLs
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(images.size) { index -> // Or imageUrl for network images
                AsyncImage(
                    model = images[index], // Use Coil/Glide for URLs
                    contentDescription = "$title Image",
                    contentScale = ContentScale.Crop,
                    onError = {
                        Log.d(
                            "ProfileScreen",
                            "Error loading image: ${it.result.throwable.message}"
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(150.dp),
                    error = painterResource(R.drawable.internet),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // Wrap in a theme for preview if needed
    ProfileScreenStateless()
}