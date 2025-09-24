package com.elitec.madriguera.presentation.screens.homeInternal

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.navigation.HomeScreens
import com.elitec.madriguera.presentation.navigation.InternalHome
import io.appwrite.models.Room
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.getScopeId
import java.time.format.DateTimeFormatterBuilder
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ReservationScreen(
    room: Room, // Habitación seleccionada desde HomeScreen
    onConfirmReservation: (LocalDate, LocalDate) -> Unit, // Callback para confirmar
    navigateTo: (HomeScreens) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Estados para fechas
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var isStartDateSelected by remember { mutableStateOf(true) } // Para alternar entre start/end
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var datePickerError by remember { mutableStateOf("") }
    // Estados separados para DatePicker
    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis =  Clock.System.now().toEpochMilliseconds(),
        yearRange = IntRange(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year + 1
        )
    )
    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
        yearRange = IntRange(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year + 1
        )
    )
    var datePickerState by remember { mutableStateOf(startDatePickerState) }

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
        modifier = modifier
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
                        .height(150.dp)
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
                            ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                ) {
                    Image(
                        painter = image,
                        contentDescription = "Imagen de la habitación",
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
                    ) {}
                }
                // Títulos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.displaySmall,
                        text = "Reserva tu habitación",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = room.name ?: "Habitación seleccionada",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
                // Selector de fechas
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp
                        ),
                        text = "Selecciona tus fechas",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // TextField para fecha de inicio
                    OutlinedTextField(
                        value = startDate?.let { startDatePickerState.selectedDateMillis?.toFormattedDateString() } ?: "dd/mm/yyyy",
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Check-in",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                isStartDateSelected = true
                                isDatePickerVisible = true
                            }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Seleccionar fecha de inicio",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        readOnly = true,
                        colors =  OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            errorContainerColor = MaterialTheme.colorScheme.error,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    // TextField para fecha de fin
                    OutlinedTextField(
                        value =  endDate?.let { endDatePickerState.selectedDateMillis?.toFormattedDateString() } ?: "dd/mm/yyyy",
                        onValueChange = { },
                        label = {
                            Text(
                                text = "Check-out",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                isStartDateSelected = false
                                isDatePickerVisible = true
                            }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Seleccionar fecha de fin",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        readOnly = true,
                        colors =  OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            errorContainerColor = MaterialTheme.colorScheme.error,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    // Mensaje de error
                    if (isError) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                // Resumen de reserva
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(
                        start = 10.dp,
                        end = 10.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resumen",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Precio por noche: $${room.precio}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        val totalNights = startDate?.let { start ->
                            endDate?.let { end ->
                                start.daysUntil(end)
                            } ?: 0
                        } ?: 0
                        val totalPrice = totalNights * (room.precio?.toDouble() ?: 0.0)
                        Text(
                            text = "Total: $${String.format("%.2f", totalPrice)} ($totalNights noches)",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                // Botón de confirmar
                Button(
                    onClick = {
                        val start = startDate
                        val end = endDate
                        if (start == null || end == null) {
                            errorMessage = "Por favor, selecciona fechas válidas"
                            isError = true
                        } else if (end <= start || end > start.plus(30, DateTimeUnit.DAY)) {
                            errorMessage = "El rango debe ser de máximo 30 días"
                            isError = true
                        } else {
                            onConfirmReservation(start, end)
                        }
                    },
                    enabled = startDate != null && endDate != null && endDate!! > startDate!! && endDate!! <= startDate!!.plus(30, DateTimeUnit.DAY),
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
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirmar reserva",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Confirmar Reserva",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        // Botón de volver
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { navigateTo(InternalHome) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Volver"
                    )
                }
            }
        }
    }
    // Modal del DatePicker
    if (isDatePickerVisible) {
        Dialog(onDismissRequest = { isDatePickerVisible = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (isStartDateSelected) "Selecciona fecha de check-in" else "Selecciona fecha de check-out",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    datePickerState = if (isStartDateSelected) startDatePickerState else endDatePickerState
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.fillMaxWidth(),
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface,
                            headlineContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            weekdayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                            selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                            todayContentColor = MaterialTheme.colorScheme.primary,
                            selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                            selectedYearContentColor = MaterialTheme.colorScheme.onPrimary
                        ),

                    )
                    if (datePickerError.isNotEmpty()) {
                        Text(
                            text = datePickerError,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = {
                            isDatePickerVisible = false
                            datePickerError = ""
                        }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    val selectedDate = Instant.fromEpochMilliseconds(selectedMillis)
                                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                                    val tomorrow = today.plus(1, DateTimeUnit.DAY)
                                    val oneYearFromToday = today.plus(365, DateTimeUnit.DAY)
                                    if (selectedDate >= today && selectedDate < oneYearFromToday) {
                                        if (isStartDateSelected) {
                                            startDate = selectedDate
                                            isStartDateSelected = false
                                        } else {
                                            endDate = selectedDate
                                        }
                                        isDatePickerVisible = false
                                        datePickerError = ""
                                    } else {
                                        datePickerError = "Selecciona una fecha entre mañana y el próximo año"
                                    }
                                } else {
                                    datePickerError = "Por favor, selecciona una fecha válida"
                                }
                            }
                        ) {
                            Text("Seleccionar")
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalTime::class)
fun Long?.toFormattedDateString(): String {
    val instant = Instant.fromEpochMilliseconds(this ?: System.currentTimeMillis())
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val formatter = LocalDate.Format {
        // Format: 25/02/2025
        dayOfMonth(); char('/'); monthNumber(); char('/'); year()
    }
    return formatter.format(date)
}

fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate? {
    return try {
        val formatter: DateTimeFormat<LocalDate> = LocalDate.Format {
            when (pattern) {
                "yyyy-MM-dd" -> {
                    year(); char('-'); monthNumber(); char('-'); dayOfMonth()
                }
                "dd/MM/yyyy" -> {
                    dayOfMonth(); char('/'); monthNumber(); char('/'); year()
                }
                else -> throw IllegalArgumentException("Formato no soportado: $pattern")
            }
        }
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}