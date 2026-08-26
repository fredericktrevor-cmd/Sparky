package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.AudioNarrationBar
import com.example.ui.components.AvatarDisplay
import com.example.ui.components.CelebrationConfetti
import com.example.ui.components.InteractiveChoiceCard
import com.example.ui.components.VisualCueCard
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SocialSparkViewModel

@Composable
fun ScenarioPlayerScreen(
    viewModel: SocialSparkViewModel,
    modifier: Modifier = Modifier
) {
    val scenarioState by viewModel.activeScenarioState.collectAsStateWithLifecycle()
    val avatarConfig by viewModel.avatarConfig.collectAsStateWithLifecycle()
    val parentalSettings by viewModel.parentalSettings.collectAsStateWithLifecycle()
    val isSpeaking by viewModel.tts.isSpeaking.collectAsStateWithLifecycle()
    val isMuted by viewModel.tts.isMuted.collectAsStateWithLifecycle()
    val lastSpokenText by viewModel.tts.lastSpokenText.collectAsStateWithLifecycle()

    val state = scenarioState ?: return

    if (state.isScenarioCompleted) {
        // VICTORY / COMPLETION SCREEN
        ScenarioCompletionView(
            scenarioTitle = state.scenario.title,
            earnedStars = state.earnedStars,
            stickerId = state.scenario.unlockStickerId,
            sensoryCalm = parentalSettings.sensoryComfortMode,
            onGoHome = { viewModel.navigateTo(AppScreen.Home) }
        )
    } else {
        val currentStep = state.currentStep
        val filteredChoices = currentStep.choices.take(parentalSettings.difficulty.choiceCount)

        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDF8FD))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("scenario_player_scroll"),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Navigation Bar (Back, Title, Step Dots)
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFCAC4D0)),
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { viewModel.navigateTo(AppScreen.Home) }
                                .testTag("scenario_back_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Return to Home",
                                    tint = Color(0xFF1C1B1F),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = state.scenario.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1B1F)
                                )
                            )
                            // Step dots
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                state.scenario.steps.forEachIndexed { idx, _ ->
                                    val isCurrent = idx == state.currentStepIndex
                                    val isPassed = idx < state.currentStepIndex
                                    Box(
                                        modifier = Modifier
                                            .size(if (isCurrent) 12.dp else 8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    isCurrent -> Color(0xFF6750A4)
                                                    isPassed -> Color(0xFF2E7D32)
                                                    else -> Color(0xFFEADDFF)
                                                }
                                            )
                                    )
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF3EDF7)
                        ) {
                            Text(
                                text = "Step ${state.currentStepIndex + 1}/${state.scenario.steps.size}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6750A4)
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // Audio Narration Companion Bar
                item {
                    AudioNarrationBar(
                        isSpeaking = isSpeaking,
                        isMuted = isMuted,
                        lastSpokenText = lastSpokenText,
                        onPlayOrRepeat = {
                            viewModel.speakText("${currentStep.title}. ${currentStep.narrationText}")
                        },
                        onStop = { viewModel.stopAudio() },
                        onToggleMute = { viewModel.toggleMuteAudio() }
                    )
                }

                // Illustrated Scenario Scene Card
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            // Scene Image
                            val drawableResId = when (state.scenario.imageResName) {
                                "img_scenario_playground" -> R.drawable.img_scenario_playground
                                "img_scenario_classroom" -> R.drawable.img_scenario_classroom
                                "img_scenario_calm_corner" -> R.drawable.img_scenario_calm_corner
                                else -> R.drawable.img_hero_buddy
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = drawableResId),
                                    contentDescription = state.scenario.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                // Peer Character Reaction Badge
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color.White.copy(alpha = 0.95f),
                                    border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(text = currentStep.peerExpression, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = currentStep.peerName,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1C1B1F)
                                            )
                                        )
                                    }
                                }
                            }

                            // Story prompt & narration text
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = currentStep.title,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF21005D)
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentStep.storyPrompt,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color(0xFF49454F)
                                    )
                                )
                            }
                        }
                    }
                }

                // Interactive Visual PECS Cue
                item {
                    VisualCueCard(
                        visualCue = currentStep.visualCue,
                        onPlayAudio = {
                            viewModel.speakText("Visual cue: ${currentStep.visualCue.pecsLabel}. ${currentStep.visualCue.audioCueText}")
                        }
                    )
                }

                // Avatar Companion Speech Bubble
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AvatarDisplay(
                            config = avatarConfig,
                            size = 72.dp,
                            isSpeaking = isSpeaking,
                            speechBubbleText = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF0E6FF),
                            border = BorderStroke(1.dp, Color(0xFFEADDFF)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = currentStep.companionSpeech,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF21005D)
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { viewModel.speakText(currentStep.companionSpeech) },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Speak companion dialogue",
                                        tint = Color(0xFF6750A4),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Sensory Tip (if present)
                if (currentStep.sensoryTip != null) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFEADDFF),
                            border = BorderStroke(1.dp, Color(0xFF21005D).copy(alpha = 0.1f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(text = "🎧", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = currentStep.sensoryTip,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFF21005D),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }

                // What Would You Do? Question Header
                item {
                    Text(
                        text = "What is the best choice?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Behavioral Choice Cards
                items(filteredChoices.size) { index ->
                    val choice = filteredChoices[index]
                    InteractiveChoiceCard(
                        choice = choice,
                        isSelected = state.selectedChoiceId == choice.id,
                        showFeedback = state.selectedChoiceId == choice.id && state.showFeedback,
                        onSelect = {
                            viewModel.selectScenarioChoice(choice.id)
                        }
                    )
                }

                // Action / Next Step Button
                if (state.isStepCompleted) {
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            onClick = { viewModel.nextScenarioStep() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("scenario_next_step_button")
                        ) {
                            Icon(
                                imageVector = if (state.isLastStep) Icons.Default.Check else Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (state.isLastStep) "Finish Story & Get Reward! 🌟" else "Great Job! Next Step ➡️",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Confetti on positive answer
            if (state.isStepCompleted) {
                CelebrationConfetti(sensoryCalm = parentalSettings.sensoryComfortMode)
            }
        }
    }
}

@Composable
fun ScenarioCompletionView(
    scenarioTitle: String,
    earnedStars: Int,
    stickerId: String,
    sensoryCalm: Boolean,
    onGoHome: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CelebrationConfetti(particleCount = 50, sensoryCalm = sensoryCalm)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("scenario_completion_view")
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFEF3C7),
                border = BorderStroke(4.dp, Color(0xFFF59E0B)),
                modifier = Modifier.size(110.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "🏆", fontSize = 52.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mission Accomplished!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "You practiced super social skills in:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Text(
                text = scenarioTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Star Reward Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFEF3C7),
                border = BorderStroke(2.dp, Color(0xFFF59E0B)),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(text = "⭐", fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "+$earnedStars Shining Stars!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFB45309)
                            )
                        )
                        Text(
                            text = "New sticker unlocked in your album!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF92400E)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onGoHome,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("completion_home_button")
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back to Adventures",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
