package com.elitec.madriguera.presentation.theme

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

// Tema claro inspirado en apps como Booking.com y Airbnb
private val LightColorScheme = lightColorScheme(
    // Azul marino para botones de reserva (confianza)
    primary = Color(0xFF003580),
    onPrimary = Color(0xFFFFFFFF), // Texto blanco sobre primary
    // Azul cielo para contenedores como selectores de fechas
    primaryContainer = Color(0xFF47A1DE),
    onPrimaryContainer = Color(0xFFFFFFFF),
    // Rojo coral para acentos y botones secundarios (energía)
    secondary = Color(0xFFFF385C),
    onSecondary = Color(0xFFFFFFFF),
    // Rojo claro para fondos de tarjetas de ofertas
    secondaryContainer = Color(0xFFFFEBEE),
    onSecondaryContainer = Color(0xFFC62828),
    // Turquesa para iconos y etiquetas de ofertas (aventura)
    tertiary = Color(0xFF00A699),
    onTertiary = Color(0xFFFFFFFF),
    // Turquesa claro para contenedores de promociones
    tertiaryContainer = Color(0xFFE0F2F1),
    onTertiaryContainer = Color(0xFF00695C),
    // Fondo blanco puro para pantallas principales
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF212121), // Texto oscuro sobre fondo
    // Gris claro para superficies como tarjetas
    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF212121),
    // Gris medio para superficies alternativas
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF424242),
    // Rojo para mensajes de error (e.g., fechas inválidas)
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF),
    // Gris para bordes
    outline = Color(0xFFBDBDBD)
)

// Tema oscuro adaptado para baja luz, manteniendo la esencia
private val DarkColorScheme = darkColorScheme(
    // Azul más claro para visibilidad en oscuro
    primary = Color(0xFF1976D2),
    onPrimary = Color(0xFF000000),
    // Azul claro para contenedores
    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF000000),
    // Naranja rojizo atenuado para acentos
    secondary = Color(0xFFFF5722),
    onSecondary = Color(0xFF000000),
    // Naranja claro para fondos secundarios
    secondaryContainer = Color(0xFFFFCCBC),
    onSecondaryContainer = Color(0xFF5D4037),
    // Turquesa oscuro para iconos y ofertas
    tertiary = Color(0xFF26A69A),
    onTertiary = Color(0xFF000000),
    // Turquesa claro para contenedores
    tertiaryContainer = Color(0xFFB2DFDB),
    onTertiaryContainer = Color(0xFF004D40),
    // Fondo gris oscuro para pantallas
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0), // Texto claro
    // Gris oscuro para superficies
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    // Gris medio oscuro para variantes
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFBDBDBD),
    // Rojo oscuro para errores
    error = Color(0xFFF44336),
    onError = Color(0xFF000000),
    // Gris claro para bordes
    outline = Color(0xFF9E9E9E)
)
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */

@Composable
fun MadrigueraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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