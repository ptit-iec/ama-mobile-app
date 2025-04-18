package com.iec.makeup.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.R
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFC1CC
import com.iec.makeup.ui.theme.ColorFFE4E1
import com.iec.makeup.ui.theme.ColorFFF0F5
import qrcode.color.Colors

@Preview
@Composable
private fun BottomPreview() {
    BottomNavigationBar(
        {},
        TopLevelDestination.Page1
    )
}

@Composable
fun BottomNavigationBar(
    onTopLevelClick: (TopLevelDestination) -> Unit,
    currentDestination: TopLevelDestination
){
    return Card(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ColorFFF0F5
        )
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // PAGE 1
            IconState(currentDestination == TopLevelDestination.Page1, TopLevelDestination.Page1, onTopLevelClick)
            // PAGE 2
            IconState(currentDestination == TopLevelDestination.Page2, TopLevelDestination.Page2, onTopLevelClick)
            // PAGE 3
            IconState(currentDestination == TopLevelDestination.Page3, TopLevelDestination.Page3, onTopLevelClick)
            // PAGE 4
            IconState(currentDestination == TopLevelDestination.Page4, TopLevelDestination.Page4, onTopLevelClick)
        }
    }
}

@Composable
fun IconState(isChosen: Boolean, icon: TopLevelDestination, onClick: (TopLevelDestination) -> Unit){
    return Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick(icon)
        }
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = if(isChosen) painterResource(icon.selectedIcon) else painterResource(icon.unSelectedIcon),
            contentDescription = ""
        )
        Text(
            text = stringResource(icon.iconText),
            color = if(isChosen) ColorDB7093 else Color.DarkGray,
            style = TextStyle(),
            fontSize = 12.sp
        )
    }
}