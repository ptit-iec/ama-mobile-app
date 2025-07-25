package com.iec.makeup.ui.features.home.screen_expert_detail_information

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.iec.makeup.data.remote.dto.Samples
import com.iec.makeup.data.remote.dto.UserReviewExpertDTO
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.screen_expert_detail_information.ui_components.ItemReviewCard
import com.iec.makeup.ui.theme.ColorFAF9F9
import com.iec.makeup.ui.theme.colorBackground


enum class PagerTab(val title: String) {
    PROFILE("Hồ Sơ"),
    REVIEW("Đánh giá")
}

@Composable
fun ProfileScreen(
    navBack: () -> Unit = {},
    navToBookingScreen: (String) -> Unit = {},
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
        state = state.value,
        navToBookingScreen = navToBookingScreen
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
    ),
    navToBookingScreen: (String) -> Unit = {}
) {
    var pagerType by remember { mutableStateOf(PagerTab.PROFILE) }
    Scaffold(
        topBar = { ProfileTopAppBar(navBack) },
        containerColor = ColorFAF9F9
    ) { paddingValues ->
        state.expertData?.let {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
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
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Description: ",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = state.expertData.description
                                ?: "Chưa cập nhật Chưa cập nhật Chưa cập nhật Chưa cập nhật Chưa cập nhật Chưa cập nhật",
                            fontSize = 14.sp,
                            color = Color.Black,

                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding( bottom = 8.dp)
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                            .background(
                                color = colorBackground,
                                shape = RoundedCornerShape(26.dp)
                            ), // Add some padding around the row

                        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute space evenly between items
                        verticalAlignment = Alignment.CenterVertically // Vertically center the items
                    ) {

                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .clickable {
                                    pagerType = PagerTab.PROFILE
                                },
                        ) {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = pagerType == PagerTab.PROFILE,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                text = "Uploaded",
                                fontWeight = if (pagerType == PagerTab.PROFILE) FontWeight.Bold else FontWeight.Normal,
                                color = Color.Black // Example color for text
                            )
                        }

                        // Vertical Separator
                        Divider(
                            color = Color.Black, // Color of the separator
                            modifier = Modifier
                                .height(12.dp) // Set the height of the separator
                                .width(2.dp) // Set the width of the separator
                        )

                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .clickable {
                                    pagerType = PagerTab.REVIEW
                                },
                        ) {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = pagerType == PagerTab.REVIEW,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                text = "Reviews",
                                fontWeight = if (pagerType == PagerTab.REVIEW) FontWeight.Bold else FontWeight.Normal,
                                color = Color.Black // Example color for text
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
                            val data = state.expertData.samplesByCategory.map {
                                it.samples.map { samples ->
                                    samples.image
                                }
                            }.flatten()

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(data.size) { index ->
                                    AsyncImage(
                                        model = data[index] ,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentDescription = null,
                                    )
                                }
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
                            val data = state.userReviews
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                items(data.size) { index ->
                                    ReviewSession(data[index])
                                }
                            }
                        }
                    }


                }

                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ){
                    ActionButtons(){
                        navToBookingScreen(state.expertData.id ?: "")
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .background(
                    color = colorBackground,
                    shape = CircleShape
                )
                .padding(8.dp)
                .size(22.dp)
                .clickable {
                    navBack()
                }
        )
        Text(
            text = "Expert",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Default.BookmarkBorder,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .background(
                    color = colorBackground,
                    shape = CircleShape
                )
                .padding(8.dp)
                .size(22.dp)
                .clickable {
                }
        )
    }
}

// --- Profile Header Section ---
@Composable
fun ProfileHeader(
    avatar: String? = null,
    name: String = "Vu Hoai Nam",
    address: String? = null,
    description: String? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar Image
            AsyncImage(
                model = avatar
                    ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm9eMKD3IaYPOi2BSD_6rpVNf2tkdndzUtcA&s",
                contentDescription = "Avatar of ",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(82.dp)
                    .height(82.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    modifier = Modifier.wrapContentWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = address ?: "",
                    modifier = Modifier.wrapContentWidth(),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                ) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star Icon",
                            tint = Color(0xFFFF9800), // Example orange color for star
                            modifier = Modifier.size(16.dp) // Set icon size
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "4.8/5",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewSession(review: UserReviewExpertDTO){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Avatar Image
                AsyncImage(
                    model = review.userAvatar
                        ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm9eMKD3IaYPOi2BSD_6rpVNf2tkdndzUtcA&s",
                    contentDescription = "Avatar of ",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = review.userName ?: "",
                        modifier = Modifier.wrapContentWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Row(
                    ) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star Icon",
                                tint = Color(0xFFFF9800), // Example orange color for star
                                modifier = Modifier.size(16.dp) // Set icon size
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "4.8/5",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = review.makeupType ?: "",
                        modifier = Modifier.wrapContentWidth(),
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                }
            }

            Text(
                text = review.comment ?: "",
                modifier = Modifier.wrapContentWidth(),
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ReviewSession(
        review = UserReviewExpertDTO()
    )
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
            onClick = {
                onActionClick()
            },
            modifier = Modifier.wrapContentWidth(), // Takes up half the available space
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)) // Pink color
        ) {
            Text(stringResource(R.string.book), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // Wrap in a theme for preview if needed
    ProfileScreenStateless()
}