package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
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
import com.example.data.model.ChoiceOption
import com.example.data.model.VisualCue

@Composable
fun VisualCueCard(
    visualCue: VisualCue,
    modifier: Modifier = Modifier,
    onPlayAudio: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFEADDFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("visual_cue_card")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Visual PECS symbol badge
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF0E6FF),
                    border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = visualCue.symbolEmoji,
                            fontSize = 26.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF3EDF7)
                    ) {
                        Text(
                            text = "VISUAL CUE: ${visualCue.pecsLabel.uppercase()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 0.6.sp,
                                color = Color(0xFF6750A4)
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = visualCue.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                }
            }

            // Audio Cue Speaker Button
            IconButton(
                onClick = onPlayAudio,
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF6750A4))
                    .testTag("play_cue_audio_button")
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Read visual cue audio",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun InteractiveChoiceCard(
    choice: ChoiceOption,
    isSelected: Boolean,
    showFeedback: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            showFeedback && choice.isPositive -> Color(0xFF2E7D32)
            showFeedback && !choice.isPositive -> Color(0xFFF57F17)
            isSelected -> Color(0xFF6750A4)
            else -> Color(0xFFEADDFF)
        },
        label = "border_anim"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            showFeedback && choice.isPositive -> Color(0xFFE8F5E9)
            showFeedback && !choice.isPositive -> Color(0xFFFFF3E0)
            isSelected -> Color(0xFFF3EDF7)
            else -> Color.White
        },
        label = "container_anim"
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || showFeedback) 2.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onSelect)
            .testTag("choice_card_${choice.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Choice Emoji Icon
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFF3EDF7),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = choice.iconEmoji,
                            fontSize = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Choice Text
                Text(
                    text = choice.text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1B1F)
                    ),
                    modifier = Modifier.weight(1f)
                )

                // Feedback Icon Indicator
                if (showFeedback) {
                    Spacer(modifier = Modifier.width(8.dp))
                    if (choice.isPositive) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Correct Choice",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(26.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Try Again",
                            tint = Color(0xFFF57F17),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            // Feedback Explanation when chosen
            if (showFeedback) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (choice.isPositive) Color(0xFFC8E6C9) else Color(0xFFFFE0B2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = choice.feedbackTitle,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (choice.isPositive) Color(0xFF1B5E20) else Color(0xFFE65100)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = choice.feedbackDescription,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (choice.isPositive) Color(0xFF2E7D32) else Color(0xFFBF360C)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 Tip: ${choice.socialTip}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF49454F)
                            )
                        )
                    }
                }
            }
        }
    }
}

