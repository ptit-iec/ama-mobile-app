package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.ui.features.home.helpers.OrderStatusType
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFF69B4
import com.iec.makeup.ui.theme.ColorFFC1CC


@Composable
fun RecentlyViewedItems() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(5) { index ->
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
        }
    }
}


@Composable
fun OrderStatusChips(
    viewToPay: () -> Unit = {},
    viewToReceive: () -> Unit = {},
    viewToReview: () -> Unit = {},
    currentSelected: OrderStatusType
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier.clickable {
                viewToPay()
            }
        ) {
            OrderChip("To Pay", isSelected = currentSelected == OrderStatusType.TO_PAY)
        }
        Box(
            modifier = Modifier.clickable {
                viewToReceive()
            }
        ) {
            OrderChip(
                "To Receive",
                hasNotification = true,
                isSelected = currentSelected == OrderStatusType.TO_RECEIVE
            )
        }
        Box(
            modifier = Modifier.clickable {
                viewToReview()
            }
        ) {
            OrderChip("To Review", isSelected = currentSelected == OrderStatusType.TO_REVIEW)
        }
    }
}

@Composable
fun OrderChip(
    text: String,
    hasNotification: Boolean = false,
    isSelected: Boolean = false
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) ColorDB7093 else ColorFFC1CC)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            if (hasNotification) {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StoreyItemsPreview() {
    StoriesItems()
}

@Composable
fun StoriesItems() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(3) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(240.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    if (index == 0) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Green)
                                .padding(horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "LIVE",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RounedCardPreview() {
    StunningRoundedCard(
        title = "Nguyen Van A",
        subtitle = "100+ books this month",
        buttonText = "See details",
        onButtonClick = {}
    )
}

@Composable
fun StunningRoundedCard(
    imageURL: String = "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
    title: String = "Nguyen Van A",
    subtitle: String = "100+ books this month",
    buttonText: String = "See details",
    onButtonClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)

            .clickable {
                onItemClick(title)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, ColorDB7093)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            // Image with rounded corners on top
            Card(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .shadow(
                        elevation = 32.dp,
                        shape = CircleShape,
                        ambientColor = ColorFF69B4,

                        spotColor = ColorFF69B4,
                    ),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
//                AsyncImage(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentScale = ContentScale.Crop,
//                    model = imageURL,
//                    contentDescription = "",
//                    placeholder = painterResource(id = R.drawable.pick1_edit),
//                    onLoading = {
//                    }
//                )
                Image(
                    painter = painterResource(R.drawable.funny_jake_adventure_time_cute_yellow_desktop_wallpaper_4k),
                    contentDescription = "Logo",
                    contentScale = ContentScale.FillHeight
                )
            }

            // Content padding
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // Subtitle
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    maxLines = 2,

                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.5.sp,
                    style = TextStyle(
                        lineHeight = 16.sp
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )

                // Spacer
                Spacer(modifier = Modifier.height(4.dp))

                // Action Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Star",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "4.5",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier,
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = ColorDB7093
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                text = buttonText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}