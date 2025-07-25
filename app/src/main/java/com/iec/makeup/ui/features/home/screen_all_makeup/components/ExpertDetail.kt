package com.iec.makeup.ui.features.home.screen_all_makeup.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.remote.dto.Rating
import com.iec.makeup.ui.theme.ColorDB7093

@Composable
fun ExpertDetail(
    item: Expert,
    onBookNowClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavToDetail: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavToDetail(item.Id ?: "Update soon")
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ColorDB7093),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            AsyncImage(
                model = item.avatar,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape) // Optional border
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.weight(1f) // Allow text content to take remaining space
            ) {
                // Name
                Text(
                    text = item.name ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63) // Pink color for name
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Profession/Description
                Text(
                    text = item.description ?: "",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom Row (Distance, Rating, Reviews)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Distance
                    Icon(
                        imageVector = Icons.Filled.NearMe, // Replace with your location icon resource
                        contentDescription = "Location",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.rating?.average.toString() ?: "",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Rating and Reviews
                    Icon(
                        painter = painterResource(
                            R.drawable.star
                        ), // Replace with your star icon resource
                        contentDescription = "Rating",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Gray)) {
                                append("${item.rating?.average} ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.Underline,
                                    color = Color.Gray
                                )
                            ) {
                                append("(${item.rating?.count} reviews)")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ExpertDetail(
        item = Expert(
            name = "Dr. Arlene McCoy",
            avatar = "https://i.ytimg.com/vi/DYkKy3FtSv8/maxresdefault.jpg",
            description = "Chưa cập nhật",
            rating = Rating(
                count = 5,
                average = 4.5,
                display = "4.5"
            )
        ),
        onBookNowClick = {}
    )
}