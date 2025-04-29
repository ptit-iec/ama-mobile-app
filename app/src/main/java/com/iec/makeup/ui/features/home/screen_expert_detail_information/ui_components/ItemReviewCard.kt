package com.iec.makeup.ui.features.home.screen_expert_detail_information.ui_components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.core.utils.getDistance
import com.iec.makeup.data.remote.dto.UserReviewExpertDTO
import com.iec.makeup.ui.theme.ColorDB7093


@Preview
@Composable
private fun Preview() {
    ItemReviewCard()
}

@SuppressLint("DefaultLocale")
@Composable
fun ItemReviewCard(
    item: UserReviewExpertDTO = UserReviewExpertDTO(),
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp)
            .height(224.dp),
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
            modifier = Modifier.fillMaxSize().clickable {
                expanded = !expanded
            },
            contentAlignment = Alignment.BottomCenter
        ){
            AsyncImage(
                model  = item.image ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm9eMKD3IaYPOi2BSD_6rpVNf2tkdndzUtcA&s",
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                error = painterResource(R.drawable.internet)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black,
                            )
                        )
                    )
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.userName ?: "Ngo Tuan Anh",
                    color = Color.White,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Kiểu: ${item.makeupType ?: "Chưa cập nhật"}",
                        color = Color.White,
                        fontSize = 11.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(100.dp),
                        maxLines = 1,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "${item.rating} / 5.0 " ,
                        color = Color.White,
                        fontSize = 11.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = item.comment ?: "Chưa cập nhật",
                    color = Color.White,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    maxLines = if (expanded) 3 else 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}