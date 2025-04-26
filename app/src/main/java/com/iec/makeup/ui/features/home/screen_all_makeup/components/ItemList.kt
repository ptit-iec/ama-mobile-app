package com.iec.makeup.ui.features.home.screen_all_makeup.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.remote.dto.Rating
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFF0F5

@Composable
fun MakeUpItemCard(
    item: Expert,
    onBookNowClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavToDetail: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clickable {
            onNavToDetail(item.name ?: "Chưa cập nhật")
        },
        colors = CardDefaults.cardColors(containerColor = ColorFFF0F5),
        border = BorderStroke(1.dp, ColorDB7093)
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
                        model = item.avatar,
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = item.name ?: "Chưa cập nhật",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black // Or MaterialTheme.colorScheme.onSurface
                        )
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
                                    imageVector = Icons.Filled.NearMe,
                                    contentDescription = "Rating Star",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    modifier = Modifier.widthIn(max = 50.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    text = item.rating?.average.toString() + "km",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White // Or MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Text(
                        text = item.description ?: "Chưa cập nhật",
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        color = Color.Gray // Or MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))


            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Middle Section: Availability & Fee ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // No shadow
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Price: xxxx",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Availability: xxxx",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating Star",
                        tint = ColorDB7093
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.rating?.average.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${item.rating?.count} đã đánh giá)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = onBookNowClick,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorDB7093),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.see_profile),
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
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