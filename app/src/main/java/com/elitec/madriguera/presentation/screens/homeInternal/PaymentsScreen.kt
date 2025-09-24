package com.elitec.madriguera.presentation.screens.homeInternal

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.rememberDialogState
import com.elitec.madriguera.R
import com.elitec.madriguera.domain.entities.types.PaymentMethod
import com.elitec.madriguera.presentation.components.PaymentMethodCard
import com.elitec.madriguera.presentation.navigation.HomeScreens
import com.elitec.madriguera.presentation.navigation.Reservation
import io.appwrite.models.Room
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil

@SuppressLint("DefaultLocale")
@Composable
fun PaymentsScreen(
    modifier: Modifier = Modifier,
    room: Room,
    startDate: String,
    endDate: String,
    onConfirmPayment: (PaymentMethod, String?) -> Unit,
    navigateTo: (HomeScreens) -> Unit
) {

    val dialogState = rememberDialogState()

    // Parsear fechas desde strings a LocalDate
    val parsedStartDate = startDate.toLocalDate("yyyy-MM-dd")
    val parsedEndDate = endDate.toLocalDate("yyyy-MM-dd")

    // Estados
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var mobileNumber by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Scroll
    val scrollState = rememberScrollState()

    LaunchedEffect(parsedStartDate, parsedEndDate) {
        if (parsedStartDate == null || parsedEndDate == null) {
            isError = true
            errorMessage = "Fechas inválidas"
        } else if (parsedEndDate <= parsedStartDate) {
            isError = true
            errorMessage = "La fecha de check-out debe ser posterior al check-in"
        } else {
            isError = false
            errorMessage = ""
        }
    }

    // Animación de entrada
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
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                // Imagen de la habitación
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                            spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        )
                ) {
                    room.photo?.let { image ->
                        Image(
                            painter = painterResource(image.toInt()),
                            contentDescription = "Imagen de ${room.name}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xCB0a0a0a)
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = room.name ?: "",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .drawBehind {
                                    drawCircle(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        radius = 10f,
                                        center = Offset(size.width / 2, size.height / 2)
                                    )
                                }
                        )
                        TextButton(
                            onClick = { /* No hace nada aquí */ },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Previsualizar pago",
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Pagar",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
                // Título
                Text(
                    text = "Selecciona tu método de pago",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                // Métodos de pago
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PaymentMethodCard(
                        method = PaymentMethod.Cash,
                        isSelected = selectedPaymentMethod == PaymentMethod.Cash,
                        onSelect = {
                            selectedPaymentMethod = PaymentMethod.Cash
                            dialogState.visible = true
                        }
                    )
                    PaymentMethodCard(
                        method = PaymentMethod.Transfermovil,
                        isSelected = selectedPaymentMethod == PaymentMethod.Transfermovil,
                        onSelect = {
                            selectedPaymentMethod = PaymentMethod.Transfermovil
                            dialogState.visible = true
                        }
                    )
                    PaymentMethodCard(
                        method = PaymentMethod.Balance,
                        isSelected = selectedPaymentMethod == PaymentMethod.Balance,
                        onSelect = {
                            selectedPaymentMethod = PaymentMethod.Balance
                            dialogState.visible = true
                        }
                    )
                }
                // Instrucciones según método
                /*AnimatedVisibility(
                    visible = selectedPaymentMethod != null,
                    enter = fadeIn(animationSpec = tween(300))
                ) {
                    selectedPaymentMethod?.let { method ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = when (method) {
                                        PaymentMethod.Cash -> "Pago en Efectivo"
                                        PaymentMethod.Transfermovil -> "Pago por Transfermovil"
                                        PaymentMethod.Balance -> "Pago por Saldo"
                                    },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = when (method) {
                                        PaymentMethod.Cash -> "Contacta al anfitrión para coordinar el pago en efectivo. Número de contacto: +53 1234-5678 (simulado)."
                                        PaymentMethod.Transfermovil -> "Realiza una transferencia bancaria a la cuenta: 1234-5678-9012-3456 (simulada). Ingresa el código de confirmación en Transfermovil."
                                        PaymentMethod.Balance -> "Transfiere el saldo al número móvil indicado. Ingresa el número al que enviarás el saldo."
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                if (method == PaymentMethod.Balance) {
                                    OutlinedTextField(
                                        value = mobileNumber,
                                        onValueChange = { mobileNumber = it },
                                        label = { Text("Número de móvil") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                            focusedBorderColor = MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }*/
                // Resumen de reserva
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resumen de la reserva",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Habitación: ${room.name}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Fechas: $startDate - $endDate",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Precio por noche: $${room.precio}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        val totalNights = if (parsedStartDate != null && parsedEndDate != null) {
                            parsedStartDate.daysUntil(parsedEndDate)
                        } else {
                            0
                        }
                        val totalPrice = totalNights * (room.precio?.toDouble() ?: 0.0)
                        Text(
                            text = "Total: $${String.format("%.2f", totalPrice)} ($totalNights noches)",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                // Mensaje de error
                if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
                // Botón de confirmar
                Button(
                    onClick = {
                        when (selectedPaymentMethod) {
                            null -> {
                                errorMessage = "Selecciona un método de pago"
                                isError = true
                            }
                            PaymentMethod.Balance -> {
                                if (mobileNumber.length != 8 || !mobileNumber.all { it.isDigit() }) {
                                    errorMessage = "Ingresa un número de móvil válido (8 dígitos)"
                                    isError = true
                                } else {
                                    onConfirmPayment(selectedPaymentMethod!!, mobileNumber)
                                }
                            }
                            else -> onConfirmPayment(selectedPaymentMethod!!, null)
                        }
                    },
                    enabled = selectedPaymentMethod != null && (selectedPaymentMethod != PaymentMethod.Balance || mobileNumber.length == 8),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirmar pago",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Confirmar Pago",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                // Botón de volver
                TextButton(
                    onClick = { navigateTo(Reservation(room.id)) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Volver",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    Dialog(state = dialogState) {
        DialogPanel(
            modifier = Modifier
                .displayCutoutPadding()
                .systemBarsPadding()
                .widthIn(min = 280.dp, max = 560.dp)
                .padding(30.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFE4E4E4), RoundedCornerShape(12.dp))
                .background(Color.White),
        ) {
            Column {
                selectedPaymentMethod?.let { method ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Image(
                                painter = when(method) {
                                    PaymentMethod.Cash -> painterResource(R.drawable.cash)
                                    PaymentMethod.Transfermovil -> painterResource(R.drawable.transfermovil)
                                    PaymentMethod.Balance -> painterResource(R.drawable.etecsa)
                                },
                                contentDescription = "Payments Methods",
                                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp))
                            )
                            Text(
                                text = when (method) {
                                    PaymentMethod.Cash -> "Pago en Efectivo"
                                    PaymentMethod.Transfermovil -> "Pago por Transfermovil"
                                    PaymentMethod.Balance -> "Pago por Saldo"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.background
                            )
                            Text(
                                text = when (method) {
                                    PaymentMethod.Cash -> "Contacte al encargado de reservas mas cerca de usted para coordinar el pago en efectivo,." // Hay que definir esto
                                    PaymentMethod.Transfermovil -> "En este caso, se realiza la prereserva de la habitacion y se confirma una vez coordinado el pago con el encargado de reservas mas cercano" // Hay que rotar la tarjeta o utilizar la de la empresa
                                    PaymentMethod.Balance -> "Transfiere el saldo al número móvil indicado. Ingresa el número al que enviarás el saldo." // Hay que definir esto bien
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                color = MaterialTheme.colorScheme.background,
                                style = MaterialTheme.typography.bodyLarge,
                                text = "Encargado de reservas: +53 68349082 (Adrian)" // Declarar los reservantes mas cercanos
                            )
                        }
                    }
                }
            }
        }
    }
}