package com.iec.makeup.ui.features.profiles.booking_history.components.booked_screen.booking_detail

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
private fun Preview() {
    ItemDetailInfo()
}

@Composable
fun ItemDetailInfo(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White)
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Image
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray) // Replace with actual image
                ) {
                    // Replace with real image loading logic
                    // AsyncImage(painter = painterResource(id = R.drawable.profile_image), contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    // Name
                    Text(
                        text = "Bùi Mai Linh",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF4081) // Pink color for name
                    )
                    // Description
                    Text(
                        text = "Makeup dự tiệc, tại nhà",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // Date
                Text(
                    text = "06/02/2025 20:00-22:00",
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Total Amount
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Tổng tiền: 350.000 VND",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF4081) // Pink color for amount
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Check Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}