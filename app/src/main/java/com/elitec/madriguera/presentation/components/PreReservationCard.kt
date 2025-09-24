package com.elitec.madriguera.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.composeunstyled.Button
import com.composeunstyled.Icon
import com.elitec.madriguera.domain.entities.types.PreReservationStatus
import com.elitec.madriguera.presentation.util.PreReservation
import kotlinx.datetime.daysUntil

@SuppressLint("DefaultLocale")
@Composable
fun PreReservationCard(
    preReservation: PreReservation,
    onConfirm: (String, String) -> Unit
) {
    // Estado para el código
    var confirmationCode by remember { mutableStateOf("") }

    // Colores según estado
    val cardColor = when (preReservation.status) {
        PreReservationStatus.PENDING -> Color(0xFFFFD54F) // Ámbar cálido
        PreReservationStatus.CONFIRMED -> Color(0xFF81C784) // Verde esmeralda suave
        PreReservationStatus.REJECTED -> Color(0xFFEF5350) // Rojo coral
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                color = Color.Black,
                text = preReservation.room.name ?: "Habitación",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                color = Color.DarkGray,
                text = "Fechas: ${preReservation.startDate} - ${preReservation.endDate}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${
                    when (preReservation.status) {
                        PreReservationStatus.PENDING -> "Pendiente"
                        PreReservationStatus.CONFIRMED -> "Confirmada"
                        PreReservationStatus.REJECTED -> "Desestimada"
                    }
                }",
                style = MaterialTheme.typography.bodyMedium,
                color = when (preReservation.status) {
                    PreReservationStatus.PENDING -> Color(0xFFE65100) // Naranja oscuro
                    PreReservationStatus.CONFIRMED -> Color(0xFF1B5E20) // Verde oscuro
                    PreReservationStatus.REJECTED -> Color(0xFFB71C1C) // Rojo oscuro
                }
            )
            val totalNights = preReservation.startDate.daysUntil(preReservation.endDate)
            Text(
                color = Color.Black,
                text = "Total: $${String.format("%.2f", totalNights * (preReservation.room.precio?.toDouble() ?: 0.0))} ($totalNights noches)",
                style = MaterialTheme.typography.bodyMedium
            )
            // Campo para código (solo si está pendiente)
            if (preReservation.status == PreReservationStatus.PENDING) {
                OutlinedTextField(
                    value = confirmationCode,
                    onValueChange = { confirmationCode = it },
                    label = { Text("Código de confirmación") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { onConfirm(preReservation.id, confirmationCode) },
                    enabled = confirmationCode.length == 6 && confirmationCode.all { it.isDigit() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirmar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Confirmar Reserva",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}