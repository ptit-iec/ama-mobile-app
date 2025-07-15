package com.iec.makeup.ui.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.core.utils.getDistance
import com.iec.makeup.ui.features.home.helpers.OrderStatusType
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.primaryColorV2


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
            .background(if (isSelected) ColorDB7093 else primaryColorV2)
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
        onButtonClick = {}
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun StunningRoundedCard(
    item: Expert = Expert(),
    onButtonClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(180.dp)
            .clickable {
                item.Id?.let {
                    onItemClick(it)
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, ColorDB7093)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            coil.compose.AsyncImage(
                model  = item.avatar ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm9eMKD3IaYPOi2BSD_6rpVNf2tkdndzUtcA&s",
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()

            )
            Column(
                modifier = Modifier.fillMaxWidth().height(60.dp).background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.DarkGray
                        )
                    )
                ).padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.name ?: "Ngo Tuan Anh",
                    color = Color.White,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NearMe,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${String.format("%.1f", item.location?.getDistance(20.980918,105.7848362))} km",

                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = if ((item.rating?.count ?: 0) > 0) "${item.rating!!.average} / 5.0" else "Chưa có đánh giá",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize =  if ((item.rating?.count ?: 0) > 0) 12.sp else 9.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}