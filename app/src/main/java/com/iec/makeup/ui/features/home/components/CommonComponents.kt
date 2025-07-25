package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.iec.makeup.core.model.ui.Expert

@Preview(showBackground = true)
@Composable
private fun RoundedCornerShape() {
    ExpertCard(
        expert = Expert(
        )
    )
}

@Composable
fun ExpertCard(
    expert: Expert,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(expert.Id ?: "")
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar Image
            AsyncImage(
                model = expert.avatar ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm9eMKD3IaYPOi2BSD_6rpVNf2tkdndzUtcA&s",
                contentDescription = "Avatar of ${expert.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .height(78.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Information Column
            Column(modifier = Modifier.weight(1f)) {
                // Top Row: Name and Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Name
                    Text(
                        text = expert.name ?: "Unknown Expert",
                        modifier = Modifier.weight(1f, fill = false),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                R.drawable.star
                            ),
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${expert.rating?.average}/5.0",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }


                // Location
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.NearMe, // Best match from Material Icons
                        contentDescription = "Distance",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "5 km",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                // Description
                Text(
                    text = expert.description ?: "",
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

