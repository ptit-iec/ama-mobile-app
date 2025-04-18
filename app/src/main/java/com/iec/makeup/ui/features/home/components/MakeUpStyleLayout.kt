package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iec.makeup.core.model.ui.MakeUpLayout
import com.iec.makeup.ui.theme.ColorDB7093


@Composable
fun MakeUpStyleLayout(
    item: MakeUpLayout
) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(0.dp),
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = ColorDB7093
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MakeUpStylePreview(){
    MakeUpStyleLayout(
        item = MakeUpLayout(
            id = "1",
            title = "Portrait",
            image = "https://images.unsplash.com/photo-153452874"
        )
    )
}