package com.iec.makeup.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.R

@Preview(showBackground = true
)
@Composable
private fun LogoCompPre() {
    LogoComponent()
}


@Composable
fun LogoComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.pick1_edit),
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
        )
//        Text(
//            text = "GlamAura",
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            fontFamily = FontFamily.Cursive,
//            modifier = Modifier.padding(start = 4.dp)
//        )
    }
}