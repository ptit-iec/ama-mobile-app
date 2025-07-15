package com.iec.makeup.ui.features.booking

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.core.ui.IECText
import com.iec.makeup.ui.theme.primaryColor

@Composable
fun BookingScreen(
    navBack: () -> Unit = {},
    navToComplete: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ĐẶT LỊCH HẸN",
                        color = primaryColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // User Information Section
            Text(
                "Thông tin của Nguyễn Thảo Linh:",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(
                    icon = Icons.Default.LocationOn,
                    text = "122 Hoàng Quốc Việt, Cổ Nhuế,\nCầu Giấy, Hà Nội"
                )
                Spacer(modifier = Modifier.width(8.dp))
                InfoCard(
                    icon = Icons.Default.Phone,
                    text = "0828421384"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Appointment Details Section
            Text(
                "Cuộc hẹn makeup của bạn:",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            AppointmentDetailRow(
                icon = Icons.Default.Person,
                label = "Chuyên gia:",
                value = "Bùi Mai Linh"
            )
            AppointmentDetailRow(
                icon = Icons.Default.Code, // Using Code as a placeholder icon
                label = "Chọn hình thức",
                value = "Tại nhà",
                showDropdown = true
            )
            AppointmentDetailRow(
                icon = Icons.Default.CalendarToday,
                label = "Chọn thời gian",
                value = "06/02/2025 20:00",
                showDropdown = true // Represents a date/time picker
            )
            AppointmentDetailRow(
                icon = Icons.Default.FavoriteBorder,
                label = "Loại makeup:",
                value = "Dự tiệc",
                showImageIcon = true // Represents an image/icon next to the value
            )
            AppointmentDetailRow(
                icon = Icons.Default.AttachMoney, // Using AttachMoney as a placeholder icon
                label = "Giá tiền makeup:",
                value = "350.000VNĐ"
            )
            AppointmentDetailRow(
                icon = Icons.Default.DirectionsCar, // Using DirectionsCar as a placeholder icon
                label = "Chi phí di chuyển:",
                value = "0VNĐ"
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the total and button to the bottom

            // Total Price Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tổng tiền:", fontSize = 18.sp, color = Color.Black)
                Text(
                    "350.000VNĐ",
                    fontSize = 20.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmation Button
            Button(
                onClick = {
                    navToComplete()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Xác nhận", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, text: String) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(icon, contentDescription = null, tint = primaryColor)
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
            }
            IECText("0384132699", color = Color.Black)
            IECText(text,color = Color.Black)
        }
    }
}

@Composable
fun AppointmentDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    showDropdown: Boolean = false,
    showImageIcon: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, color = Color.Black, modifier = Modifier.width(120.dp))
        Text(value, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        if (showImageIcon) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.Image,
                contentDescription = "Image",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            ) // Placeholder for image icon
        }
        if (showDropdown) {
            Spacer(modifier = Modifier.weight(1f)) // Pushes the dropdown icon to the end
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookingScreen() {
    BookingScreen()
}