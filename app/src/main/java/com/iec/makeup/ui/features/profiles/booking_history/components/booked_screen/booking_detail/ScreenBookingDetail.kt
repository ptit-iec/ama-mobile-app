package com.iec.makeup.ui.features.profiles.booking_history.components.booked_screen.booking_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R
import com.iec.makeup.ui.theme.ColorDB7093

@Composable
fun ScreenBookingDetail(
    navBack: () -> Unit = {},
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .size(24.dp)
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.booking_detail),
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = ColorDB7093,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }
    ) { padding ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column{
                    ItemAddress()
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemDetailInfo()
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemDateBook()
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* Handle cancel action */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Hủy lịch hẹn",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }

                    // Contact Expert Button with gray outline
                    Button(
                        onClick = { /* Handle contact action */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Liên hệ chuyên gia",
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = ScreenBookingDetail()