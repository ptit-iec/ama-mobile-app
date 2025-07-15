package com.iec.makeup.ui.features.profiles.booking_history.components.canceled_screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.ui.IECText
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import com.iec.makeup.ui.features.profiles.booking_history.model.mockListTest
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun ScreenCanceledBooking(
    data: List<UserBookingUIWrapper> = mockListTest
){
    LazyColumn {
        items(data.size) { index ->
            val item = data[index]
            ItemCanceledBooking(
                data = item
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ItemCanceledBooking(
    data: UserBookingUIWrapper
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color(0xFFFFC1CC), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            AsyncImage(
                model = data.image,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop

            )

            Spacer(modifier = Modifier.width(16.dp))

            // Service Details
            Column(
                modifier = Modifier
            ) {
                // Name
                Text(
                    text = "Bùi Mai Linh",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Service Description
                Text(
                    text = "Makeup dự tiệc, tại nhà",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                // Price
                Text(
                    text = "Tổng tiền: 350.000 VNĐ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                // Cancellation Reason
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancel Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Lý do: Không còn nhu cầu",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            // Right Column: Date, Time, and Contact Button
            Column(
                horizontalAlignment = Alignment.End,
//                modifier = Modifier.widthIn(max = 80.dp)
            ) {
                // Cancelled Badge
                IECText(
                    text = "Huỷ",
                    color = Color.Red,
                    modifier = Modifier
                        .background(Color(0xFFFFE6E6), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date and Time
                IECText(
                    text = "06/02/2025 20:00-22:00",
                    color = Color.Gray,
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Contact Button
                Box(
                    modifier = Modifier.clip(
                        RoundedCornerShape(8.dp)
                    ).background(color = ColorDB7093).padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Liên hệ",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemCanceledBooking(
        data = mockListTest[0]
    )
}