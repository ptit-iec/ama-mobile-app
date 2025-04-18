package com.iec.makeup.ui.features.home.screen_booking.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.sp
import com.iec.makeup.R

@Preview
@Composable
private fun IntroPreview() {
    IntroduceComponent()
}

@Composable
fun IntroduceComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light grey background
            .padding(bottom = 16.dp) // Add padding at the bottom if needed
    ) {
        // Top section with Back button and Profile details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp), // Padding for the top area
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp)) // Space between back button and image

            // Profile Image with Online Indicator
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.pick1_edit), // Replace with your drawable
                    contentDescription = "Dr. Kevon Lane",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, Color.White), CircleShape) // Optional border
                )
                // Green online status indicator
                Box(
                    modifier = Modifier
                        .padding(bottom = 4.dp, end = 4.dp) // Adjust padding for position
                        .size(24.dp)
                        .background(Color.White, CircleShape) // White circle behind green dot
                        .padding(3.dp) // Padding inside white circle
                        .background(Color(0xFF00C853), CircleShape) // Green dot color
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = "Dr. Kevon Lane",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // Use appropriate color from theme if available
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Specialty
            Text(
                text = "Gynecologist",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Qualifications
            Text(
                text = "MBBS, BCS, (Health), MCPS (Gynae & Obs), MRCOG (Gynae & Obs) (UK).",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding if text is long
            )

            Spacer(modifier = Modifier.height(24.dp)) // Space before the cards
        }

        // Info Cards Section - Use Column to stack them vertically
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp), // Horizontal padding for the cards
            verticalArrangement = Arrangement.spacedBy(16.dp) // Space between cards
        ) {
            // Experience and Rating Card
            InfoCardRow()

            // Consultation Fee Card
            ConsultationFeeCard()
        }
    }
}

@Composable
fun InfoCardRow() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround // Distribute space
        ) {
            InfoItem(
                icon = Icons.Default.WorkOutline,
                label = "Total Experience",
                value = "05+ Years"
            )
            // Simple vertical divider (optional)
            // Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.LightGray)
            InfoItem(
                icon = Icons.Default.Star,
                label = "Rating",
                value = "4.9 (500)"
            )
        }
    }
}

@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Gray, // Adjust tint as needed
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black // Adjust color as needed
        )
    }
}

@Composable
fun ConsultationFeeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE3F2FD), CircleShape) // Light blue background
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Consultation Fee",
                    tint = Color(0xFF0D47A1), // Darker blue tint for icon
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Column
            Column {
                Text(
                    text = "$200",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // Adjust color as needed
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Consultation fee",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}