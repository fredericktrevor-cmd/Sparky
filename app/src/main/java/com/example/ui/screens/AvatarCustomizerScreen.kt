package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AuraSparkle
import com.example.data.model.AvatarBuddy
import com.example.data.model.AvatarConfig
import com.example.data.model.AvatarExpression
import com.example.data.model.AvatarOutfit
import com.example.data.model.SensoryAccessory
import com.example.ui.components.AvatarDisplay
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SocialSparkViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AvatarCustomizerScreen(
    viewModel: SocialSparkViewModel,
    modifier: Modifier = Modifier
) {
    val savedConfig by viewModel.avatarConfig.collectAsStateWithLifecycle()
    var currentConfig by remember(savedConfig) { mutableStateOf(savedConfig) }
    var kidNameInput by remember(savedConfig.kidName) { mutableStateOf(savedConfig.kidName) }
    val isSpeaking by viewModel.tts.isSpeaking.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("avatar_customizer_scroll"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        .testTag("customizer_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "Avatar Dress-Up 🦸",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                IconButton(
                    onClick = {
                        viewModel.speakText("Customize your buddy! Tap different buddies, sensory headphones, and cool outfits!")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read instructions",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Live Avatar Preview Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = BorderStroke(2.5.dp, Color(currentConfig.buddy.primaryColor).copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    AvatarDisplay(
                        config = currentConfig.copy(kidName = kidNameInput),
                        size = 130.dp,
                        isSpeaking = isSpeaking,
                        speechBubbleText = "I look super awesome!"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = kidNameInput,
                        onValueChange = {
                            kidNameInput = it
                            currentConfig = currentConfig.copy(kidName = it)
                        },
                        label = { Text("My Name") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("kid_name_input")
                    )
                }
            }
        }

        // 1. Choose Companion Buddy
        item {
            CustomizerSectionCard(title = "1. Choose Your Buddy", icon = "🤖") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AvatarBuddy.entries.forEach { buddy ->
                        val isSelected = currentConfig.buddy == buddy
                        val emoji = when (buddy) {
                            AvatarBuddy.SPARK_ROBOT -> "🤖"
                            AvatarBuddy.LEO_LION -> "🦁"
                            AvatarBuddy.BELLA_BUNNY -> "🐰"
                            AvatarBuddy.SAM_EXPLORER -> "🧑‍🚀"
                        }
                        SelectionChip(
                            label = buddy.displayName.split(" ").first(),
                            icon = emoji,
                            isSelected = isSelected,
                            accentColor = Color(buddy.primaryColor),
                            onClick = {
                                currentConfig = currentConfig.copy(buddy = buddy)
                                viewModel.speakText("You picked ${buddy.displayName}!")
                            }
                        )
                    }
                }
            }
        }

        // 2. Expressions
        item {
            CustomizerSectionCard(title = "2. Face Expression", icon = "😊") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AvatarExpression.entries.forEach { expr ->
                        val isSelected = currentConfig.expression == expr
                        SelectionChip(
                            label = expr.label,
                            icon = expr.emoji,
                            isSelected = isSelected,
                            accentColor = MaterialTheme.colorScheme.primary,
                            onClick = {
                                currentConfig = currentConfig.copy(expression = expr)
                                viewModel.speakText(expr.audioHint)
                            }
                        )
                    }
                }
            }
        }

        // 3. Sensory Gear & Accessories
        item {
            CustomizerSectionCard(title = "3. Sensory Super-Gear", icon = "🎧") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SensoryAccessory.entries.forEach { accessory ->
                        val isSelected = currentConfig.accessory == accessory
                        SelectionChip(
                            label = accessory.label,
                            icon = accessory.iconEmoji,
                            isSelected = isSelected,
                            accentColor = Color(accessory.colorHex),
                            onClick = {
                                currentConfig = currentConfig.copy(accessory = accessory)
                                viewModel.speakText("${accessory.label}. ${accessory.description}")
                            }
                        )
                    }
                }
            }
        }

        // 4. Outfits
        item {
            CustomizerSectionCard(title = "4. Super Outfits", icon = "🧥") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AvatarOutfit.entries.forEach { outfit ->
                        val isSelected = currentConfig.outfit == outfit
                        val emoji = when (outfit) {
                            AvatarOutfit.HERO_CAPE -> "🦸"
                            AvatarOutfit.COZY_HOODIE -> "🧥"
                            AvatarOutfit.STAR_TEE -> "⭐"
                            AvatarOutfit.DINO_SUIT -> "🦖"
                            AvatarOutfit.RAINBOW_JACKET -> "🌈"
                        }
                        SelectionChip(
                            label = outfit.label,
                            icon = emoji,
                            isSelected = isSelected,
                            accentColor = Color(outfit.colorHex),
                            onClick = {
                                currentConfig = currentConfig.copy(outfit = outfit)
                                viewModel.speakText("Looking great in your ${outfit.label}!")
                            }
                        )
                    }
                }
            }
        }

        // 5. Aura Glow
        item {
            CustomizerSectionCard(title = "5. Sparkle Aura", icon = "✨") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AuraSparkle.entries.forEach { aura ->
                        val isSelected = currentConfig.aura == aura
                        SelectionChip(
                            label = aura.label,
                            icon = "✨",
                            isSelected = isSelected,
                            accentColor = Color(aura.colorHex),
                            onClick = {
                                currentConfig = currentConfig.copy(aura = aura)
                                viewModel.speakText("${aura.label} aura activated!")
                            }
                        )
                    }
                }
            }
        }

        // Save & Apply Button
        item {
            Button(
                onClick = {
                    viewModel.updateAvatar(currentConfig.copy(kidName = kidNameInput.trim()))
                    viewModel.speakText("Avatar saved! Let's go on adventures, ${kidNameInput.ifBlank { "Champion" }}!")
                    viewModel.navigateTo(AppScreen.Home)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("save_avatar_button")
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save & Use Avatar ✨",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CustomizerSectionCard(
    title: String,
    icon: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun SelectionChip(
    label: String,
    icon: String,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = icon, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) accentColor else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
