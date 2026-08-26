package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class ConfettiParticle(
    val startX: Float,
    val startY: Float,
    val speedX: Float,
    val speedY: Float,
    val color: Color,
    val radius: Float
)

@Composable
fun CelebrationConfetti(
    modifier: Modifier = Modifier,
    particleCount: Int = 35,
    sensoryCalm: Boolean = false
) {
    val progress = remember { Animatable(0f) }

    val particles = remember {
        val colors = if (sensoryCalm) {
            listOf(
                Color(0xFF81C784),
                Color(0xFF64B5F6),
                Color(0xFFFFD54F),
                Color(0xFFBA68C8)
            )
        } else {
            listOf(
                Color(0xFFFF5722),
                Color(0xFFFFEB3B),
                Color(0xFF4CAF50),
                Color(0xFF2196F3),
                Color(0xFFE91E63),
                Color(0xFFFFC107)
            )
        }

        List(if (sensoryCalm) 20 else particleCount) {
            ConfettiParticle(
                startX = Random.nextFloat(),
                startY = -0.1f - (Random.nextFloat() * 0.3f),
                speedX = (Random.nextFloat() - 0.5f) * if (sensoryCalm) 150f else 300f,
                speedY = 400f + Random.nextFloat() * if (sensoryCalm) 250f else 500f,
                color = colors.random(),
                radius = 6f + Random.nextFloat() * 8f
            )
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = if (sensoryCalm) 2500 else 1800,
                easing = LinearEasing
            )
        )
    }

    if (progress.value < 1f) {
        Box(modifier = modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val t = progress.value

                particles.forEach { p ->
                    val x = (p.startX * canvasWidth) + (p.speedX * t)
                    val y = (p.startY * canvasHeight) + (p.speedY * t)
                    val alpha = (1f - (t * 0.8f)).coerceIn(0f, 1f)

                    drawCircle(
                        color = p.color.copy(alpha = alpha),
                        radius = p.radius,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}
