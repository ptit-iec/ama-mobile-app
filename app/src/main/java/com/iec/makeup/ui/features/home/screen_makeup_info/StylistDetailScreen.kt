package com.iec.makeup.ui.features.home.screen_makeup_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navBack: () -> Unit = {},
    id: String = "Vu Hoai Nam"
) {
    Scaffold(
        topBar = { ProfileTopAppBar(navBack) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Make the whole column scrollable
        ) {
            ProfileHeader(id)
            ProfileStats()
            ActionButtons()
            ImageSection(title = "Make up", images = makeupImages) // Pass actual data
            Spacer(modifier = Modifier.height(16.dp))
            ImageSection(title = "Skin care", images = skincareImages) // Pass actual data
            Spacer(modifier = Modifier.height(16.dp)) // Bottom padding
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
                Icon(Icons.Filled.Share, contentDescription = "Share")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface, // Or Color.White
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// --- Profile Header Section ---
@Composable
fun ProfileHeader(
    id: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.account_circle_24dp_df9d9b_fill0_wght400_grad0_opsz24), // Replace with actual avatar
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray.copy(alpha = 0.5f), CircleShape) // Optional border
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$id",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "PTIT, Ha Noi, Viet Nam",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "http://blog.MakeupByMario.com",
            fontSize = 14.sp,
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
            .padding( horizontal = 24.dp), // Add horizontal padding
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
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
    ) {
        Button(
            onClick = { /* Handle Book action */ },
            modifier = Modifier.weight(1f), // Takes up half the available space
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)) // Pink color
        ) {
            Text("Book", color = Color.White)
        }
        OutlinedButton(
            onClick = { /* Handle Follow action */ },
            modifier = Modifier.weight(1f), // Takes up the other half
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp) // Thinner border if needed
        ) {
            Text("Follow") // Text color defaults appropriately
        }
    }
}


// --- Reusable Image Section (Make up, Skin care) ---
@Composable
fun ImageSection(title: String, images: List<Int>) { // Use List<String> for URLs
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
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Expand $title",
                tint = Color.Gray
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .heightIn(max = 500.dp) // Constrain height if needed, LazyGrid handles scroll within
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(images) { imageResId -> // Or imageUrl for network images
                Image(
                    painter = painterResource(id = imageResId), // Use Coil/Glide for URLs
                    contentDescription = "$title Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f) // Make images square
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

// --- Placeholder Data (Replace with your actual data source) ---
val makeupImages =
    List(3) { R.drawable.chatgpt_image_apr_2__2025__01_14_53_am } // Repeat placeholder
val skincareImages =
    List(3) { R.drawable.chatgpt_image_apr_2__2025__01_14_53_am } // Repeat placeholder

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // Wrap in a theme for preview if needed
    ProfileScreen()
}