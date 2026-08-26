package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuraSparkle
import com.example.data.model.AvatarBuddy
import com.example.data.model.AvatarConfig
import com.example.data.model.AvatarExpression
import com.example.data.model.AvatarOutfit
import com.example.data.model.SensoryAccessory

@Composable
fun AvatarDisplay(
    config: AvatarConfig,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    isSpeaking: Boolean = false,
    speechBubbleText: String? = null,
    onSpeakClick: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "avatar_motion")
    
    // Gentle idle breathing or lively speaking bounce
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isSpeaking) -8f else -3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isSpeaking) 250 else 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "avatar_bounce"
    )

    val auraPulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aura_pulse"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.testTag("avatar_display_container")
    ) {
        // Speech Bubble if present
        if (!speechBubbleText.isNullOrBlank()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 6.dp,
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(config.buddy.primaryColor)),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .testTag("avatar_speech_bubble")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "💬",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = speechBubbleText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }

        // Avatar Core Container
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .offset(y = bounceOffset.dp)
        ) {
            // Glowing Aura
            val auraColor = Color(config.aura.colorHex).copy(alpha = 0.35f)
            Box(
                modifier = Modifier
                    .size(size * 1.1f)
                    .scale(auraPulse)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(auraColor, Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )

            // Outer Base Ring
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size)
                    .shadow(8.dp, CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(config.buddy.primaryColor),
                                Color(config.buddy.secondaryColor)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(3.dp, Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                // Character Face / Head Representation
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)
                ) {
                    // Buddy Base Character Glyph & Expression
                    val buddyIcon = when (config.buddy) {
                        AvatarBuddy.SPARK_ROBOT -> "🤖"
                        AvatarBuddy.LEO_LION -> "🦁"
                        AvatarBuddy.BELLA_BUNNY -> "🐰"
                        AvatarBuddy.SAM_EXPLORER -> "🧑‍🚀"
                    }

                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = buddyIcon,
                            fontSize = (size.value * 0.42f).sp
                        )

                        // Sensory Accessory (e.g. Headphones or Glasses)
                        if (config.accessory != SensoryAccessory.NONE) {
                            Text(
                                text = config.accessory.iconEmoji,
                                fontSize = (size.value * 0.28f).sp,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .offset(y = (-4).dp)
                            )
                        }
                    }

                    // Outfit & Expression Badge Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.offset(y = (-2).dp)
                    ) {
                        // Expression Emoji
                        Text(
                            text = config.expression.emoji,
                            fontSize = (size.value * 0.20f).sp,
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.9f), CircleShape)
                                .padding(2.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        // Outfit Badge
                        val outfitEmoji = when (config.outfit) {
                            AvatarOutfit.HERO_CAPE -> "🦸"
                            AvatarOutfit.COZY_HOODIE -> "🧥"
                            AvatarOutfit.STAR_TEE -> "⭐"
                            AvatarOutfit.DINO_SUIT -> "🦖"
                            AvatarOutfit.RAINBOW_JACKET -> "🌈"
                        }
                        Text(
                            text = outfitEmoji,
                            fontSize = (size.value * 0.18f).sp,
                            modifier = Modifier
                                .background(Color(config.outfit.colorHex).copy(alpha = 0.9f), CircleShape)
                                .padding(2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Kid / Buddy Tag Label
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.testTag("avatar_name_badge")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${config.kidName} & ${config.buddy.displayName.split(" ").first()}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}
