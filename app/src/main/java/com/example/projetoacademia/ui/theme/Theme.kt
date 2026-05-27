package com.example.projetoacademia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GymDarkColorScheme = darkColorScheme(
    primary = GymPrimary,
    onPrimary = GymBackground,
    primaryContainer = GymPrimaryDark,
    onPrimaryContainer = GymText,
    secondary = GymSecondary,
    onSecondary = GymText,
    secondaryContainer = GymSurfaceVariant,
    onSecondaryContainer = GymText,
    tertiary = GymWarning,
    background = GymBackground,
    onBackground = GymText,
    surface = GymSurface,
    onSurface = GymText,
    surfaceVariant = GymSurfaceVariant,
    onSurfaceVariant = GymTextMuted,
    error = GymError,
    errorContainer = GymError.copy(alpha = 0.18f),
    onErrorContainer = GymError,
    outline = GymSurfaceVariant
)

@Composable
fun ProjetoAcademiaTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = GymBackground.toArgb()
            window.navigationBarColor = GymBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = GymDarkColorScheme,
        typography = Typography,
        content = content
    )
}
