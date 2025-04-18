package com.iec.makeup.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun PreviewLoading() {
    AtomicLoadingDialog(

    )
}

@Composable
fun AtomicLoadingDialog(
    tintColor: Color = Color.White,
    containerColor: Color = Color(0xFFFF69B4),
    borderWidth: Dp = 3.dp,
    cycleDuration: Int = 1000,
    isLoading: Boolean = true, isCritical: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition("InfiniteAtomicLoaderTransition")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(cycleDuration, easing = LinearEasing)
        ),
        label = "AtomicLoaderRotation"
    )
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            Modifier
                .size(100.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
            ),

            ) {
            Box(
                modifier = Modifier
            ) {
                RotatingCircle(
                    modifier = Modifier.fillMaxSize(),
                    rotationX = 35f,
                    rotationY = -45f,
                    rotationZ = -90f + rotation,
                    borderColor = tintColor,
                    borderWidth = borderWidth
                )
                RotatingCircle(
                    modifier = Modifier.fillMaxSize(),
                    rotationX = 50f,
                    rotationY = 10f,
                    rotationZ = rotation,
                    borderColor = tintColor,
                    borderWidth = borderWidth
                )
                RotatingCircle(
                    modifier = Modifier.fillMaxSize(),
                    rotationX = 35f,
                    rotationY = 55f,
                    rotationZ = 90f + rotation,
                    borderColor = tintColor,
                    borderWidth = borderWidth
                )
            }
        }
    }
}

@Composable
fun RotatingCircle(
    modifier: Modifier,
    rotationX: Float,
    rotationY: Float,
    rotationZ: Float,
    borderWidth: Dp,
    borderColor: Color = Color.White
) {
    Canvas(modifier.graphicsLayer {
        this.rotationX = rotationX
        this.rotationY = rotationY
        this.rotationZ = rotationZ
        cameraDistance = 12f * density
    }) {
        val mainCircle = Path().apply {
            addOval(Rect(size.center, size.minDimension / 3))
        }
        val clipCenter = Offset(size.width / 2f - borderWidth.toPx(), size.height / 2f)
        val clipCircle = Path().apply {
            addOval(Rect(clipCenter, size.minDimension / 3))
        }

        // Subtract the clipping circle from the main circle
        val path = Path().apply {
            op(mainCircle, clipCircle, PathOperation.Difference)
        }

        // Draw the subtracted path
        drawPath(path, borderColor)
    }
}