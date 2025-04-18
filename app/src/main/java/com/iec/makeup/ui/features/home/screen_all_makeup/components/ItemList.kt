package com.iec.makeup.ui.features.home.screen_all_makeup.components

import androidx.compose.runtime.Composable
import com.iec.makeup.core.model.ui.MakeUpStylist
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFF0F5

@Composable
fun MakeUpItemCard(
    item: MakeUpStylist,
    onBookNowClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavToDetail: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clickable {
            onNavToDetail(item.name)
        },
        colors = CardDefaults.cardColors(containerColor = ColorFFF0F5),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Add padding around the entire card content
                .fillMaxWidth()
        ) {
            // --- Top Section: Doctor Info & Rating ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Doctor Image and Status Dot
                Box {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = "Dr. Arlene McCoy",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    // Online Status Indicator
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .background(Color.Green, CircleShape)
                            .align(Alignment.BottomEnd) // Slight offset for visibility
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Doctor Name and Specialty
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black // Or MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = item.detail,
                        fontSize = 12.sp,
                        color = Color.Gray // Or MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Rating
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = ColorDB7093
                    ),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating Star",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.rating.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White // Or MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Middle Section: Availability & Fee ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // No shadow
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp), // Padding inside the card
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Available Now Section
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start // Align text to the start
                    ) {
                        Text(
                            text = "Available Now",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black // Or MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Green Square Icon for Video Consult
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        Color.Green,
                                        RoundedCornerShape(2.dp)
                                    ) // Small green square
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Video Consult",
                                fontSize = 14.sp,
                                color = Color.Gray // Or MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Vertical Divider (optional, could use spacing)
                    // Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.LightGray)
                    Spacer(modifier = Modifier.width(16.dp)) // Use spacer instead of divider if preferred

                    // Consultation Fee Section
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start // Align text to the start
                    ) {
                        Text(
                            text = "₫${item.price}/h",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black // Or MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Consultation Fee",
                            fontSize = 14.sp,
                            color = Color.Gray // Or MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Bottom Section: Book Appointment Button ---
            Button(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorDB7093
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Book Appointment",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, // Use AutoMirrored for RTL support
                        contentDescription = "Proceed",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MakeUpItemCard(
        item = MakeUpStylist(
            id = 1.toString(),
            name = "MakeUp Stylist 1",
            imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
            priceType = "Hourly",
            price = 100.000,
            detail = "Makeup artist with a wide range of experience in various styles.",
            rating = 4.5,
            ratingCount = 100,
            location = "New York",
            isFavorite = false,
            isVerified = true,
            isOnline = true,
            isAvailable = true,
            monthlyOrder = 124,
        ),
        onBookNowClick = {}
    )
}