package com.iec.makeup.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.core.utils.clickableNotRipple
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFAF9F9

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
) {
    return Box(
        modifier = Modifier.height(80.dp).fillMaxWidth().background(color = Color.Transparent),
    ){
        Card(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // PAGE 1
                IconState(
                    currentDestination == TopLevelDestination.Page1,
                    TopLevelDestination.Page1,
                    onTopLevelClick
                )
                Spacer(modifier = Modifier.width(32.dp))
                // PAGE 2
                IconState(
                    currentDestination == TopLevelDestination.Page4,
                    TopLevelDestination.Page4,
                    onTopLevelClick
                )
            }
        }
         Box(
             modifier = Modifier.align(Alignment.TopCenter)
         ){
             MiddleIconState(
                 currentDestination == TopLevelDestination.Page2,
                 TopLevelDestination.Page2,
                 onTopLevelClick
             )
         }
    }
}

@Composable
fun IconState(
    isChosen: Boolean,
    icon: TopLevelDestination,
    onClick: (TopLevelDestination) -> Unit
) {
    return Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp).clickableNotRipple {
            onClick(icon)
        }
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = if (isChosen) painterResource(icon.selectedIcon) else painterResource(icon.unSelectedIcon),
            contentDescription = ""
        )
        Text(
            text = stringResource(icon.iconText),
            color = if (isChosen) ColorDB7093 else Color.DarkGray,
            style = TextStyle(),
            fontSize = 12.sp
        )
    }
}

@Composable
fun MiddleIconState(
    isChosen: Boolean,
    icon: TopLevelDestination,
    onClick: (TopLevelDestination) -> Unit
) {
    return Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickableNotRipple {
            onClick(icon)
        }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    ColorFAF9F9
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(50.dp)
                    .clip(CircleShape)
                    .background(ColorDB7093),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = if (isChosen) painterResource(icon.selectedIcon) else painterResource(icon.unSelectedIcon),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}