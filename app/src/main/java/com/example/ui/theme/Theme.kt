package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SparkPrimary,
    onPrimary = SparkOnPrimary,
    primaryContainer = SparkPrimaryContainer,
    onPrimaryContainer = SparkOnPrimaryContainer,
    secondary = SparkSecondary,
    onSecondary = SparkOnSecondary,
    secondaryContainer = SparkSecondaryContainer,
    onSecondaryContainer = SparkOnSecondaryContainer,
    tertiary = SparkTertiary,
    onTertiary = SparkOnTertiary,
    tertiaryContainer = SparkTertiaryContainer,
    onTertiaryContainer = SparkOnTertiaryContainer,
    background = SparkBackgroundDark,
    onBackground = SparkOnBackgroundDark,
    surface = SparkSurfaceDark,
    onSurface = SparkOnSurfaceDark,
    surfaceVariant = SparkSurfaceVariantDark,
    onSurfaceVariant = SparkOnSurfaceVariantDark,
    outline = SparkOutlineLight,
    outlineVariant = SparkOutlineVariantLight
)

private val LightColorScheme = lightColorScheme(
    primary = SparkPrimary,
    onPrimary = SparkOnPrimary,
    primaryContainer = SparkPrimaryContainer,
    onPrimaryContainer = SparkOnPrimaryContainer,
    secondary = SparkSecondary,
    onSecondary = SparkOnSecondary,
    secondaryContainer = SparkSecondaryContainer,
    onSecondaryContainer = SparkOnSecondaryContainer,
    tertiary = SparkTertiary,
    onTertiary = SparkOnTertiary,
    tertiaryContainer = SparkTertiaryContainer,
    onTertiaryContainer = SparkOnTertiaryContainer,
    background = SparkBackgroundLight,
    onBackground = SparkOnBackgroundLight,
    surface = SparkSurfaceLight,
    onSurface = SparkOnSurfaceLight,
    surfaceVariant = SparkSurfaceVariantLight,
    onSurfaceVariant = SparkOnSurfaceVariantLight,
    outline = SparkOutlineLight,
    outlineVariant = SparkOutlineVariantLight
)

@Composable
fun SocialSparkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    sensoryComfortMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (sensoryComfortMode) {
        // Calmer, softer tones with gentle contrast
        LightColorScheme.copy(
            background = PastelLavender,
            surface = SparkSurfaceLight,
            primary = SparkPrimary,
            primaryContainer = PastelLilac,
            secondary = SparkSecondary,
            secondaryContainer = PastelSky
        )
    } else if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

