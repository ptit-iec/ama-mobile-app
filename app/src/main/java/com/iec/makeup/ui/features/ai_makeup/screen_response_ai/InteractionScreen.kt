package com.iec.makeup.ui.features.ai_makeup.screen_response_ai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun InteractionScreenStateful(
    navBack: () -> Unit = {},
    navToEditScreen: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navBack()
                    }
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    InteractionScreen()
                    Spacer(modifier = Modifier.height(16.dp))
                    FeatureSelectorRow()
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorDB7093
                        ),
                        onClick = { navToEditScreen() },
                    ) {
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = "Tiếp tục",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = "Chỉnh sửa ảnh với AI !",
                        fontSize = 14.sp,
                        color = Color.DarkGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractionScreen() {
    var isFavorite by remember { mutableStateOf(false) }
    var isPicked by remember { mutableIntStateOf(0) }
    val listImage = listOf<Int>(
        R.drawable.chatgpt_image_apr_2__2025__01_14_53_am,
        R.drawable.pick2,
        R.drawable.instruction_1_2,
        R.drawable.pick1_edit,
        R.drawable.chatgpt_image_apr_2__2025__01_14_53_am,
        R.drawable.pick1_edit
    )
    Column(
        modifier = Modifier
            .padding(vertical = 18.dp, horizontal = 4.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(
                    horizontal = 32.dp
                )
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = ColorDB7093,
                    spotColor = ColorDB7093
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorDB7093,
            ),
        ) {
            Image(
                painter = painterResource(listImage[isPicked]),
                contentDescription = "j",
                contentScale = ContentScale.FillHeight
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Chọn layout mẫu phù hợp với khuôn mặt của bạn",
            color = Color.Gray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(listImage.size) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = ColorDB7093,
                            spotColor = ColorDB7093
                        )
                        .clickable {
                            isPicked = it
                        },
                ) {
                    Card(
                        modifier = Modifier.size(64.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),
                        border = if (isPicked == it) BorderStroke(
                            2.dp,
                            ColorDB7093
                        ) else BorderStroke(0.dp, Color.Transparent),
                    ) {
                        Image(
                            painter = painterResource(listImage[it]),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Image",
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun FeatureSelectorRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Add padding around the row
        horizontalArrangement = Arrangement.SpaceAround, // Distribute items evenly
        verticalAlignment = Alignment.Top // Align items' tops
    ) {
        // Item 1: Oval
        FeatureItem(
            painter = painterResource(R.drawable.cream), // Replace with your actual oval face icon painter
            label = "Oval",
            iconSize = 48.dp // Adjust size as needed
        )

        // Item 2: Fair/Light
        FeatureItem(
            painter = painterResource(R.drawable.tanning),
            label = "Facial/Light",
            iconSize = 48.dp // Adjust size as needed
        )

        // Item 3: Neutral
        FeatureItem(

            painter = painterResource(R.drawable.image_removebg_preview__6__1),
            label = "NeuTral", // Typo as in image, correct to "Neutral" if needed
            iconSize = 48.dp // Adjust size as needed
        )
    }
}

@Composable
fun FeatureItem(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null, // Use ImageVector for Material Icons
    painter: Painter? = null,  // Use Painter for drawables/vectors
    label: String,
    iconSize: androidx.compose.ui.unit.Dp = 48.dp,
    contentDescription: String? = label // Default content description
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize),
                tint = Color.Black // Or your desired default icon color
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize)
            )
        }
        Spacer(modifier = Modifier.height(4.dp)) // Space between icon and text
        Text(
            text = label,
            fontSize = 14.sp, // Adjust font size as needed
            textAlign = TextAlign.Center,
            color = Color.Black // Or your desired text color
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    InteractionScreenStateful()
}