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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SocialScenario
import com.example.ui.components.AvatarDisplay
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SocialSparkViewModel

@Composable
fun HomeScreen(
    viewModel: SocialSparkViewModel,
    modifier: Modifier = Modifier
) {
    val avatarConfig by viewModel.avatarConfig.collectAsStateWithLifecycle()
    val userProgress by viewModel.userProgress.collectAsStateWithLifecycle()
    val isSpeaking by viewModel.tts.isSpeaking.collectAsStateWithLifecycle()
    val scenarios = viewModel.repository.getAllScenarios()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8FD))
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Clean Minimalism Header: Profile Avatar, Greeting, Streak Pill & Action Buttons
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Avatar + Name + Streak Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFEADDFF),
                        border = BorderStroke(2.dp, Color(0xFF6750A4)),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.navigateTo(AppScreen.AvatarCustomizer) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = avatarConfig.expression.emoji,
                                fontSize = 24.sp
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Hi, ${avatarConfig.kidName}!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1C1B1F),
                                letterSpacing = (-0.3).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3EDF7),
                            modifier = Modifier.testTag("home_star_counter_pill")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${userProgress.currentStreakDays} DAY STREAK 🔥",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF6750A4)
                                    )
                                )
                            }
                        }
                    }
                }

                // Action Buttons: TTS Audio & Parent Lock
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Audio Readout Action
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFCAC4D0)),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                viewModel.speakText("Hi ${avatarConfig.kidName}! You have ${userProgress.totalStars} stars. Let's practice social superpowers!")
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🔊", fontSize = 18.sp)
                        }
                    }

                    // Parent Lock Button
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFD0BCFF),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.navigateTo(AppScreen.ParentDashboard) }
                            .testTag("parent_portal_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Parent Dashboard & Controls",
                                tint = Color(0xFF21005D),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Clean Minimalism Focal Hero Card (Saying Hello at the Park / Daily Practice)
        item {
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Relative Container with Lavender Box & Floating Sound Button
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(180.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(Color(0xFFF0E6FF))
                        ) {
                            AvatarDisplay(
                                config = avatarConfig,
                                size = 120.dp,
                                isSpeaking = isSpeaking,
                                speechBubbleText = null
                            )
                        }

                        // Floating Audio Button
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = Color(0xFF6750A4),
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    viewModel.speakText("Hi ${avatarConfig.kidName}! When we meet someone new, we can wave and say Hi!")
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Read prompt",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Saying Hello & Connecting",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color(0xFF21005D),
                            letterSpacing = (-0.3).sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "When we meet someone new, we can wave and say \"Hi!\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF49454F),
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Clean Progress Indicator
                    LinearProgressIndicator(
                        progress = { userProgress.progressToNextLevel },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(CircleShape),
                        color = Color(0xFF6750A4),
                        trackColor = Color(0xFFE8DEF8)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "LEVEL ${userProgress.levelNumber} • ${userProgress.totalStars} TOTAL STARS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 0.8.sp,
                            color = Color(0xFF6750A4)
                        )
                    )
                }
            }
        }

        // Clean Action Grid (Customize & Play Now buttons matching HTML)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Customize Button
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFEADDFF),
                    border = BorderStroke(1.dp, Color(0xFF21005D).copy(alpha = 0.08f)),
                    modifier = Modifier
                        .weight(1f)
                        .height(76.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { viewModel.navigateTo(AppScreen.AvatarCustomizer) }
                        .testTag("hub_avatar_button")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "🎨", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Customize",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF21005D)
                            )
                        )
                    }
                }

                // Play Now Button
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFB1EBFF),
                    border = BorderStroke(1.dp, Color(0xFF003643).copy(alpha = 0.08f)),
                    modifier = Modifier
                        .weight(1f)
                        .height(76.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            val firstScenario = scenarios.firstOrNull()
                            if (firstScenario != null) {
                                viewModel.startScenario(firstScenario.id)
                            }
                        }
                        .testTag("hub_play_now_button")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "🎮", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Play Now",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF003643)
                            )
                        )
                    }
                }
            }
        }

        // Secondary Clean Pills: Rewards & Calm Zone
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.navigateTo(AppScreen.Rewards) }
                        .testTag("hub_stickers_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(text = "🏆", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Rewards",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF21005D)
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.navigateTo(AppScreen.CalmZone) }
                        .testTag("hub_calm_zone_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(text = "🧘", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Calm Zone",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF21005D)
                            )
                        )
                    }
                }
            }
        }

        // Section Title: Social Stories
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
            ) {
                Text(
                    text = "Social Stories",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1F),
                        letterSpacing = (-0.3).sp
                    )
                )

                IconButton(
                    onClick = {
                        viewModel.speakText("Explore social stories to practice sharing, turn-taking, and communicating with friends!")
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read section help",
                        tint = Color(0xFF6750A4)
                    )
                }
            }
        }

        // Clean Minimalism Scenario Cards
        items(scenarios) { scenario ->
            ScenarioCardItem(
                scenario = scenario,
                onStart = { viewModel.startScenario(scenario.id) }
            )
        }
    }
}

@Composable
fun ScenarioCardItem(
    scenario: SocialScenario,
    onStart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, Color(0xFFEADDFF)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onStart)
            .testTag("scenario_item_${scenario.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Category Icon Badge Frame
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF0E6FF),
                border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = scenario.category.iconEmoji,
                        fontSize = 26.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF3EDF7)
                ) {
                    Text(
                        text = scenario.category.title.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 0.6.sp,
                            color = Color(0xFF6750A4)
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = scenario.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1F)
                    )
                )

                Text(
                    text = scenario.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF49454F)
                    ),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⭐ +${scenario.starsReward} Stars",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "⏱️ ~${scenario.estimatedMinutes} min",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF49454F)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Play / Start Button
            Surface(
                shape = CircleShape,
                color = Color(0xFF6750A4),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start ${scenario.title}",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

