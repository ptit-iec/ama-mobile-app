package com.iec.makeup.ui.features.booking.screen_finish_book

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R
import com.iec.makeup.ui.theme.primaryColor

@Preview
@Composable
private fun Preview() {
    ScreenBookCompleted()
}
@Composable
fun ScreenBookCompleted(
    navHome: () -> Unit = {},
) {
    val snackBar by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    BackHandler(enabled = true) {}
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primaryColor,
                        Color(0xFFFFC3C3)
                    )
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.check),
                contentDescription = "check",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp)) // Spacing between texts

            Text(
                text = "Bạn đã đặt thành công",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // Adjust color if needed
            )

            Spacer(modifier = Modifier.height(8.dp)) // Spacing between texts

            // Subtitle Text
            Text(
                text = "Vui lòng chờ xác nhận từ chuyên gia",
                fontSize = 16.sp,
                color = Color.White // Adjust color if needed
            )

            Spacer(modifier = Modifier.height(8.dp)) // Spacing between texts

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute space evenly
            ) {
                // Left Button (Trang chủ)
                Button(
                    onClick = {
                        navHome()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f) // Take equal weight
                        .padding(end = 8.dp), // Spacing between buttons
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp) // Add a slight shadow
                ) {
                    Text(
                        text = "Trang chủ",
                        color = Color.Black // Adjust color if needed
                    )
                }

                // Right Button (Đơn đã đặt)
                Button(
                    onClick = {
                        Toast.makeText(context, "Đang phát triển", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5969)), // Example pink color, adjust as needed
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f) // Take equal weight
                        .padding(start = 8.dp) // Spacing between buttons
                ) {
                    Text(
                        text = "Đơn đã đặt",
                        color = Color.White // Adjust color if needed
                    )
                }
            }
        }
    }

}