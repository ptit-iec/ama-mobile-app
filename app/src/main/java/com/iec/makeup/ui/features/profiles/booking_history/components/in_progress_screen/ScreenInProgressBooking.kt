package com.iec.makeup.ui.features.profiles.booking_history.components.in_progress_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import com.iec.makeup.ui.features.profiles.booking_history.model.mockListTest
import com.iec.makeup.ui.theme.ColorDB7093

@Composable
fun ScreenInProgressBooking(
    data: List<UserBookingUIWrapper> = mockListTest,
    onItemClick: (UserBookingUIWrapper) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    LazyColumn {
        items(data.size) { index ->
            val item = data[index]
            InProgressBookItem(
                data = item,
                onItemClick = onItemClick
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewItem() {
    ScreenInProgressBooking()
}

@Composable
fun InProgressBookItem(
    data: UserBookingUIWrapper,
    onItemClick: (UserBookingUIWrapper) -> Unit = {}
) {
    val borderColor = Color(0xFFFFA1B5) // Pinkish border color
    val buttonColor = Color(0xFFFFB6C1) // Light pink button color
    val textColorPrimary = Color.Black
    val textColorSecondary = Color.Gray
    val nameColor = Color(0xFFFF6F61) // Coral color for the name
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, ColorDB7093, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Profile image (using a placeholder)
            AsyncImage(
                model = data.image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                // Name
                Text(
                    text = data.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = nameColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Service description
                    Text(
                        text = data.type,
                        fontSize = 14.sp,
                        color = textColorSecondary
                    )
                    Text(
                        modifier = Modifier.widthIn(max = 100.dp),
                        text = "06/02/2025 20:00-22:00",
                        fontSize = 14.sp,
                        color = textColorPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End
                    )
                }

                Row {
                    Text(
                        text = "Tổng tiền: ",
                        fontSize = 14.sp,
                        color = textColorPrimary
                    )
                    Text(
                        text = "${data.totalBill} VND",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColorPrimary
                    )
                }
                Row {
                    Text(
                        text = "SĐT: ",
                        fontSize = 14.sp,
                        color = textColorPrimary
                    )
                    Text(
                        text = data.phone,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColorPrimary
                    )
                }

                Row {
                    Text(
                        text = "Địa chỉ chuyên gia: ",
                        fontSize = 14.sp,
                        color = textColorPrimary
                    )
                    Text(
                        text = data.address,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                // Complete button
                Button(
                    onClick = { /* Handle button click */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorDB7093, contentColor = buttonColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(100.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Hoàn tất",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

        }
    }
}