package com.iec.makeup.core.ui

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.TextStyle

@Composable
fun ShimmerText(
    text: String = "Ngo Tuan Anh",
    textStyle: TextStyle = TextStyle.Default.copy(color = Color.Black),
    shimmerColors: List<Color> = listOf(
        Color.Black,
        Color.DarkGray.copy(0.5f)
    ),
    easing: Easing = EaseInOut,
    animationSpec: State<Float> = rememberInfiniteTransition(label = "shimmer").animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = easing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
) {
    val brush = remember(animationSpec) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                // Define the starting X offset, beginning outside the left edge of the text
                val initialXOffset = -size.width
                // Total distance the shimmer will sweep across (double the text width for full coverage)
                val totalSweepDistance = size.width * 2
                // Calculate the current position of the shimmer based on the animation progress
                val currentPosition = initialXOffset + totalSweepDistance * animationSpec.value

                return LinearGradientShader(
                    colors = shimmerColors,
                    from = Offset(0f, 0f),
                    to = Offset(animationSpec.value, 0f)
                )
            }

        }
    }
    Text(
        text = text,
        modifier = Modifier.wrapContentSize(),
        style = textStyle.copy(brush),
    )
}