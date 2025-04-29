package com.iec.makeup.ui.features.profiles

import android.os.Looper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Using sample icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource // If using local drawables
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.ui.theme.ColorDB7093
import java.util.logging.Handler


@Composable
fun UserProfileScreenStateful(
    navToLogin: () -> Unit = {}
) {
    val viewModel: ProfileScreenVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    var isLoggingOut by remember { mutableStateOf(false) }
    UserProfileScreen(
        state = state.value,
        onLogout = {
            isLoggingOut = true
        }
    )
    if (isLoggingOut) {
        DialogCompose(
            text = "Bạn có muốn đăng xuất?",
            onCloseAction = { isLoggingOut = false },
            positiveAction = {
                isLoggingOut = false
                viewModel.logout()
                android.os.Handler(Looper.getMainLooper()).postDelayed(
                    {
                        navToLogin()
                    },
                    500
                )
            },
            negativeAction = {
                isLoggingOut = false
            },
        )
    }
}


@Composable
fun UserProfileScreen(
    state: ProfileScreenState = ProfileScreenState(),
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* You can add a title here if needed */ },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { /* TODO: Handle notification icon click */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Add overall padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Picture
            ProfilePicture(
                state.userProfile?.avatar
            )

            Spacer(modifier = Modifier.height(16.dp))

            // User Name
            UserName(name = state.userProfile?.name ?: "")

            Spacer(modifier = Modifier.height(32.dp)) // More space before buttons

            // Grid of Buttons
            ActionButtonsGrid(
                onLogout = onLogout
            )
        }
    }
}

@Composable
fun ProfilePicture(
    avatar: String?
) {
    // Using a placeholder Box for demonstration
    Box(
        modifier = Modifier
            .size(120.dp) // Adjust size as needed
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray) // Use a placeholder color or load actual image
    ) {
        AsyncImage(
            model = avatar,
            contentDescription = "Profile Picture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.internet)
        )
    }
}

@Composable
fun UserName(name: String) {
    Text(
        text = name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black // Adjust color as needed
    )
}

@Composable
fun ActionButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp) // Padding around each button card
            .width(150.dp) // Adjust width as needed
            .clickable {
                onClick()
            }
            .height(100.dp), // Adjust height as needed
        shape = RoundedCornerShape(8.dp), // Rounded corners
        elevation = 2.dp,// Card elevation,
        border = BorderStroke(1.dp, ColorDB7093)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), // Internal padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.Red, // Adjust icon color as needed
                modifier = Modifier.size(36.dp) // Adjust icon size
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black // Adjust text color
            )
        }
    }
}

@Composable
fun ActionButtonsGrid(
    onLogout: () -> Unit = {}
) {
    // Using nested Rows and Columns for a fixed 2x3 grid
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribute space
        ) {
            ActionButton(
                icon = Icons.Default.ShoppingCart,
                text = "Lịch hẹn với\nchuyên gia",
                onClick = { /* TODO: Handle click */ })
            ActionButton(
                icon = Icons.Default.Refresh,
                text = "Lịch sử\nhoạt động",
                onClick = { /* TODO: Handle click */ })
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.HelpOutline,
                text = "Trợ giúp &\nPhản hồi",
                onClick = { /* TODO: Handle click */ })
            ActionButton(
                icon = Icons.Default.Share,
                text = "Mời bạn bè",
                onClick = { /* TODO: Handle click */ })
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.List,
                text = "Điều khoản\ndịch vụ",
                onClick = { /* TODO: Handle click */ })
            ActionButton(icon = Icons.Default.Logout, text = "Đăng xuất", onClick = {
                onLogout()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    UserProfileScreen()
}