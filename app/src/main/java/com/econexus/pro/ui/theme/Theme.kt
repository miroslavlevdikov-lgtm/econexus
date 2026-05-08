package com.econexus.pro.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = NexusOceanBlue,
    onPrimary = Color.White,
    primaryContainer = NexusOceanSurface,
    onPrimaryContainer = NexusOceanDark,
    secondary = NexusTealSecondary,
    onSecondary = Color.White,
    secondaryContainer = NexusTealSurface,
    onSecondaryContainer = NexusTealSecondary,
    tertiary = NexusAmberAccent,
    onTertiary = Color.White,
    tertiaryContainer = NexusAmberLight,
    onTertiaryContainer = NexusSlateWarm,
    background = NexusWhite,
    onBackground = NexusCharcoal,
    surface = Color.White,
    onSurface = NexusCharcoal,
    surfaceVariant = NexusLightGray,
    onSurfaceVariant = NexusDarkGray,
    error = NexusError,
    errorContainer = NexusErrorContainer,
    onError = Color.White,
    outline = NexusMediumGray,
    outlineVariant = Color(0xFFCFD8DC)
)

private val DarkColorScheme = darkColorScheme(
    primary = NexusOceanLight,
    onPrimary = NexusOceanDark,
    primaryContainer = NexusOceanDark,
    onPrimaryContainer = NexusOceanSurface,
    secondary = NexusTealLight,
    onSecondary = NexusTealSecondary,
    secondaryContainer = NexusTealSecondary,
    onSecondaryContainer = NexusTealSurface,
    tertiary = NexusAmberAccent,
    onTertiary = NexusSlateWarm,
    tertiaryContainer = NexusSlateWarm,
    onTertiaryContainer = NexusAmberLight,
    background = NexusDarkBackground,
    onBackground = NexusDarkOnSurface,
    surface = NexusDarkSurface,
    onSurface = NexusDarkOnSurface,
    surfaceVariant = NexusDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFB0BEC5),
    error = Color(0xFFEF5350),
    errorContainer = Color(0xFF4E0000),
    onError = Color.White,
    outline = Color(0xFF607D8B),
    outlineVariant = Color(0xFF37474F)
)

@Composable
fun EcowResoursesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}