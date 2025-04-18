package com.iec.makeup.core.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R

@Composable
fun DialogCompose(
    negativeAction: (() -> Unit)? = null,
    positiveAction: (() -> Unit) = {},
    onCloseAction: () -> Unit = {},
    ableDismiss: Boolean = false,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.8f))
            .padding(horizontal = 12.dp)
            .clickable(){
                if (ableDismiss) onCloseAction()
            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.White),

            ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Notice",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFFFF69B4)
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { onCloseAction() }
                        )
                    }
                }
                Image(
                    painter = painterResource(R.drawable.pick1_edit),
                    contentDescription = null
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = text,
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    softWrap = true
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    if (negativeAction != null) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .clickable { negativeAction() },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No",
                                    color = Color.Black,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .clickable { positiveAction() },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFF69B4)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Agree",
                                color = Color.White,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DialogComposePreview() {
    DialogCompose(
        text = "Are you sure you want to delete this item?"
    )
}