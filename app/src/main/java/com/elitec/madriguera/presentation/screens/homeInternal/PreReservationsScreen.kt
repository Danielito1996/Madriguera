package com.elitec.madriguera.presentation.screens.homeInternal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elitec.madriguera.domain.entities.types.PreReservationStatus
import com.elitec.madriguera.presentation.components.PreReservationCard
import com.elitec.madriguera.presentation.navigation.HomeScreens
import com.elitec.madriguera.presentation.navigation.InternalHome
import com.elitec.madriguera.presentation.util.PreReservation
import io.appwrite.models.Room
import kotlinx.datetime.LocalDate
@Composable
fun PreReservationsScreen(
    userId: String,
    modifier: Modifier = Modifier,
    navigateTo: (HomeScreens) -> Unit,
    onConfirmPreReservation: (String, String) -> Unit
) {
    // Datos simulados para UI
    val preReservations = listOf(
        PreReservation(
            id = "1",
            room = Room(
                id = "1",
                name = "Habitación Deluxe",
                precio = 120.0f,
                description = "Amplia habitación con vista al mar",
                capacidad = 2,
                photo = null
            ),
            startDate = LocalDate(2025, 9, 25),
            endDate = LocalDate(2025, 9, 28),
            status = PreReservationStatus.PENDING
        ),
        PreReservation(
            id = "2",
            room = Room(
                id = "2",
                name = "Suite Premium",
                precio = 180.0f,
                description = "Suite de lujo con balcón",
                capacidad = 4,
                photo = null
            ),
            startDate = LocalDate(2025, 10, 1),
            endDate = LocalDate(2025, 10, 5),
            status = PreReservationStatus.CONFIRMED
        ),
        PreReservation(
            id = "3",
            room = Room(
                id = "1",
                name = "Habitación Deluxe",
                precio = 120.0f,
                description = "Amplia habitación con vista al mar",
                capacidad = 2,
                photo = null
            ),
            startDate = LocalDate(2025, 9, 30),
            endDate = LocalDate(2025, 10, 2),
            status = PreReservationStatus.REJECTED
        )
    )

    // Estado para errores
    var errorMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500))
    ) {
        Surface(
            shadowElevation = 8.dp,
            tonalElevation = 5.dp,
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.surface
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Título
                item {
                    Text(
                        text = "Tus Reservaciones",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                // Lista de prereservas
                items(preReservations) { preReservation ->
                    PreReservationCard(
                        preReservation = preReservation,
                        onConfirm = { id, code ->
                            if (code.length != 6 || !code.all { it.isDigit() }) {
                                errorMessage = "Código inválido (6 dígitos)"
                                isError = true
                            } else {
                                onConfirmPreReservation(id, code)
                                isError = false
                            }
                        }
                    )
                }
                // Mensaje de error
                if (isError) {
                    item {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                // Botón de volver
                item {
                    TextButton(
                        onClick = { navigateTo(InternalHome) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Volver",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                // Espaciador final
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
