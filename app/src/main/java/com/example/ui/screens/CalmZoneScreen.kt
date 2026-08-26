package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AvatarExpression
import com.example.data.model.EmotionZone
import com.example.data.model.SensoryAccessory
import com.example.ui.components.AvatarDisplay
import com.example.ui.components.SensoryBreathingBalloon
import com.example.ui.components.SensoryBubblePopper
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SocialSparkViewModel

@Composable
fun CalmZoneScreen(
    viewModel: SocialSparkViewModel,
    modifier: Modifier = Modifier
) {
    val avatarConfig by viewModel.avatarConfig.collectAsStateWithLifecycle()
    val selectedZone by viewModel.selectedEmotionZone.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("calm_zone_scroll"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(AppScreen.Home) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .testTag("calm_zone_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "The Calm Corner 🧘",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF8B5CF6)
                    )
                )

                IconButton(
                    onClick = {
                        viewModel.speakText("Welcome to the Calm Corner! You can check your feeling zone, breathe with the balloon, or pop bubbles!")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read instructions",
                        tint = Color(0xFF8B5CF6)
                    )
                }
            }
        }

        // Companion Avatar in Calm Mood
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),
                border = BorderStroke(2.dp, Color(0xFF8B5CF6).copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    AvatarDisplay(
                        config = avatarConfig.copy(
                            expression = AvatarExpression.CALM,
                            accessory = SensoryAccessory.NOISE_HEADPHONES
                        ),
                        size = 80.dp,
                        isSpeaking = false
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Take all the time you need!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5B21B6)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "This cozy space is safe, quiet, and friendly. We are here with you.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF6B21A8)
                            )
                        )
                    }
                }
            }
        }

        // 1. Emotion Zones Check-In
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🌈", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "How is your brain feeling?",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.speakText("How is your brain feeling right now? Tap a color zone to check in!")
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Read audio prompt",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4 Zone Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EmotionZone.entries.forEach { zone ->
                            val isSelected = selectedZone == zone
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) Color(zone.colorHex).copy(alpha = 0.25f) else Color(zone.colorHex).copy(alpha = 0.08f),
                                border = BorderStroke(
                                    if (isSelected) 2.5.dp else 1.dp,
                                    Color(zone.colorHex)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { viewModel.selectEmotionZone(zone) }
                                    .testTag("zone_${zone.id}")
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
                                ) {
                                    Text(text = zone.emoji, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = zone.zoneName.split(" ").first(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                            color = Color(zone.colorHex)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Selected Zone Strategy Card
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(selectedZone.colorHex).copy(alpha = 0.12f),
                        border = BorderStroke(1.5.dp, Color(selectedZone.colorHex)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = selectedZone.zoneName,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(selectedZone.colorHex)
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = selectedZone.description,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.9f)
                            ) {
                                Text(
                                    text = "💡 Calm Strategy: ${selectedZone.calmAction}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(selectedZone.colorHex)
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Interactive Sensory Breathing Balloon
        item {
            SensoryBreathingBalloon(
                onPhaseChange = { prompt ->
                    // gentle narration if needed
                }
            )
        }

        // 3. Interactive Sensory Pop-it Bubble Station
        item {
            SensoryBubblePopper(
                onBubblePopped = { count ->
                    if (count % 5 == 0) {
                        viewModel.speakText("Pop! Pop! Nice and relaxing!")
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
