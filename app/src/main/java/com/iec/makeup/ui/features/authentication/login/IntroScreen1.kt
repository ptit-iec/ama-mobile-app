package com.iec.makeup.ui.features.authentication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.util.packInts
import com.iec.makeup.R


enum class IntroScreen(
    val id: Int,
    val image: Int,
    val title: String,
    val description: String
) {
    IntroScreenA(
        id = 1,
        image = R.drawable.intro1,
        title = "AI Makeup Assistant",
        description = "Your personal beauty advisor powered by artificial intelligence"
    ),
    IntroScreenB(
        id = 2,
        image = R.drawable.intro2,
        title = "Personalized Makeup Suggestions",
        description = "Perfect makeup suggestions for dates, parties, or any special occasion"
    ),
    IntroScreenC(
        id = 3,
        image = R.drawable.intro3,
        title = "Makeup Tutorials",
        description = "Detailed guides on how to do your own perfect makeup"
    ),
    IntroScreenD(
        id = 4,
        image = R.drawable.intro4,
        title = "Connect with Experts",
        description = "Book appointments with professional makeup artists at home, in-salon, or via video call"
    ),
}

@Composable
fun IntroScreen(
    introScreen: IntroScreen
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Image(
                painter = painterResource(introScreen.image),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().heightIn(300.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = introScreen.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            ),
            color = Color(0xFFFF5969),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = introScreen.description,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewIntroScreen() {
    IntroScreen(IntroScreen.IntroScreenA)
}   