package com.iec.makeup.core.ui
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFE4E1

@Composable
fun TypingIndicator(
    modifier: Modifier = Modifier,
    color: Color = ColorDB7093
) {
    // Create 3 dots with different animation delays
    Row(
        modifier = modifier
            .background(
                color = ColorFFE4E1,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Create three animated dots with different phase offsets
        val delays = listOf(0, 150, 300)

        delays.forEach { delay ->
            AnimatedDot(
                color = color,
                delay = delay
            )
        }
    }
}

@Composable
private fun AnimatedDot(
    color: Color,
    delay: Int = 0
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Typing indicator dot animation")

    // Create animation that oscillates between 0 and 1 with a delay
    val animatedValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                0f at 0 with LinearEasing
                1f at 500 with LinearEasing
                0f at 1000 with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "Dot animation"
    )

    // Add delay using LaunchedEffect
    val animatedValueWithDelay = remember { Animatable(0f) }

    LaunchedEffect(animatedValue) {
        // Add delay based on the dot's position
        kotlinx.coroutines.delay(delay.toLong())
        animatedValueWithDelay.animateTo(
            targetValue = animatedValue,
            animationSpec = tween(durationMillis = 50)
        )
    }

    // Size of the dot changes based on the animation value
    val dotSize = 6.dp + (4.dp * animatedValueWithDelay.value)

    Box(
        modifier = Modifier
            .size(dotSize)
            .clip(CircleShape)
            .background(color)
    )
}

@Preview
@Composable
private fun Preivew() {
    TypingIndicator()
}