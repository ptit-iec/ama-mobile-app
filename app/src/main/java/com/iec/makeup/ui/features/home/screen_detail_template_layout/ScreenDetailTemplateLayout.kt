package com.iec.makeup.ui.features.home.screen_detail_template_layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.R
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun ScreenDetailTemplateLayoutStateful(
    item: MakeUpTemplateLayout,
    onApplyTemplate: (MakeUpTemplateLayout) -> Unit,
    onClose: () -> Unit
) {

    ScreenDetailTemplateLayout(
        item = item,
        onApplyTemplate = onApplyTemplate,
        onClose = onClose
    )
}


@Composable
fun ScreenDetailTemplateLayout(
    item: MakeUpTemplateLayout,
    onApplyTemplate: (MakeUpTemplateLayout) -> Unit = {},
    onClose: () -> Unit = {}

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd// Adjust aspect ratio based on your image
        ) {
            // --- Image ---

            // --- Heart Icon ---
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder, // Use Filled.Favorite if it should be filled
                contentDescription = "Like",
                tint = Color(0xFFE91E63), // Pinkish color for the heart
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp) // Padding from the corner
                    .size(32.dp) // Size of the icon
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pick1), // <<--- REPLACE with your image resource
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop, // Crop to fit bounds
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)) // Rounded corners for the image
            )
        }
        // --- Text Description ---
        Text(
            text = item.prompt,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center, // Align text to the start
            modifier = Modifier.fillMaxWidth() // Take full width within the card padding
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Buttons ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Space buttons evenly
        ) {
            // --- Close Button ---
            OutlinedButton(
                onClick = { onClose() },
                shape = RoundedCornerShape(50), // Pill shape
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp) // Give weight and padding
            ) {
                Text("Đóng")
            }

            // --- Apply Color Button ---
            Button(
                onClick = {
                    onApplyTemplate(item)
                },
                shape = RoundedCornerShape(50), // Pill shape
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorDB7093 // Light Pink color
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp) // Give weight and padding
            ) {
                Text("Áp dụng mẫu", color = Color.White) // Set text color explicitly if needed
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    ScreenDetailTemplateLayout(
        item = MakeUpTemplateLayout(
            "1",
            "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
            "Prompt 1",
            false
        )
    )
}