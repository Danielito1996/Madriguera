package com.elitec.madriguera.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.navigation.Login
import com.elitec.madriguera.presentation.navigation.MainScreens
import io.github.composefluent.component.Icon

@Composable
fun HelpScreen(
    navigateTo: (MainScreens) -> Unit,
    modifier: Modifier = Modifier
) {
    // Control de desplazamiento
    val scrollState = rememberScrollState()

    // Degradado de imagen
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0x802A2A2A)
        )
    )

    // Selección de imagen según el tema
    val image = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.hav2m)
    } else {
        painterResource(R.drawable.hav1m)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(10.dp)
    ) {
        Surface(
            shadowElevation = 8.dp,
            tonalElevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Imagen superior con halo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 20.dp
                            )
                        )
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 20.dp
                            ),
                            ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), // #47A1DE claro, #BBDEFB oscuro
                            spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                ) {
                    Image(
                        painter = image,
                        contentDescription = "Imagen de fondo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            ),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            )
                            .background(brush)
                    ) {

                    }
                }
                // Títulos y descripción
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.displaySmall,
                        text = "Acerca de Madriguera",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = "Tu app para encontrar la habitación perfecta",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = """
                            Madriguera es tu compañera ideal para reservar habitaciones únicas y acogedoras. 
                            Con una interfaz sencilla, puedes:
                            - Buscar habitaciones por fecha y ubicación.
                            - Reservar estancias con facilidad y seguridad.
                            - Gestionar tu perfil y reservas desde cualquier dispositivo.
                            
                            Regístrate con tu correo o con Google para acceder a todas las funcionalidades. 
                            ¡Explora, reserva y vive la experiencia Madriguera!
                        """.trimIndent(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                // Botón de volver
                Button(
                    onClick = { navigateTo(Login) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Icono de inicio",
                            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = "Volver a Inicio"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        // Botón de contacto (opcional)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { /* TODO: Abrir correo o formulario de contacto */ }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Icono de contacto",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Contáctanos"
                    )
                }
            }
        }
    }
}
