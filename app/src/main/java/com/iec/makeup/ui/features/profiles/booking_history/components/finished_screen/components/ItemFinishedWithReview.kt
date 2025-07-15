package com.iec.makeup.ui.features.profiles.booking_history.components.finished_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper

@Composable
fun ItemFinishedWithReview(
    data: UserBookingUIWrapper
){
    ReviewScreen(data)
}

@Composable
fun ReviewScreen(
    data: UserBookingUIWrapper
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, Color(0xFFFFA1A1), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            AsyncImage(
                model = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=880&q=80",
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Name and Description
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Nguyen Thao Linh",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "MAKEUP dự tiệc, tài năng",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Date and Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "06/02/2025 22:00",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "5",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Price
        Row {
            Text(
                text = "TỔNG TIỀN: ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "350.000 VND",
                fontSize = 14.sp,
                color = Color(0xFFFFA1A1),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Review Text
        Text(
            text = "Trải nghiệm rất tuyệt vời, Trải nghiệm rất tuyệt vời, Trải nghiệm rất tuyệt vời",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(
            visible = isExpanded
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    Image(
                        painter = painterResource(id = R.drawable.internet), // Replace with your image resource
                        contentDescription = "Review Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Hidden Section with Dropdown
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable{
                isExpanded = !isExpanded
            }
        ) {
            Text(
                text = if (isExpanded) "Ẩn" else "Chi tiết",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown ,
                contentDescription = "Dropdown",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}