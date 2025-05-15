package com.iec.makeup.ui.features.profiles.booking_history.components.booked_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import com.iec.makeup.ui.features.profiles.booking_history.model.mockListTest
import com.iec.makeup.ui.theme.ColorDB7093


@Preview
@Composable
fun ScreenBookedAppointment(
    data: List<UserBookingUIWrapper> = mockListTest,
    onItemClick: (UserBookingUIWrapper) -> Unit = {},
    onLoadMore: () -> Unit = {}
){
    LazyColumn {
        items(data.size) { index ->
            val item = data[index]
            BookedItem(
                data = item,
                onItemClick = onItemClick
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))
        }

    }
}



@Composable
fun BookedItem(
    data: UserBookingUIWrapper,
    onItemClick: (UserBookingUIWrapper) -> Unit = {}
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.wrapContentWidth()
            ){
                AsyncImage(
                    model = data.image,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFFFFC1CC), CircleShape),
                    error = painterResource(R.drawable.internet),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = data.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF5E62)
                    )
                    Text(
                        text = data.type,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Tổng tiền: ${data.totalBill} VNĐ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Chờ xác nhận",
                    fontSize = 12.sp,
                    color = Color(0xFFDA70D6)
                )
                Text(
                    text = "06/02/2025 20:00-22:00",
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle click */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorDB7093,
                        contentColor = ColorDB7093),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .height(36.dp)
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