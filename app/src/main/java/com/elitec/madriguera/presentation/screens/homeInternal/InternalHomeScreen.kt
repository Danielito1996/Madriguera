package com.elitec.madriguera.presentation.screens.homeInternal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.elitec.madriguera.presentation.components.RoomCard
import com.elitec.madriguera.presentation.navigation.HomeScreens
import com.elitec.madriguera.presentation.navigation.InternalHome
import io.appwrite.models.Room
import io.github.composefluent.component.Icon

@Composable
fun InternalHomeScreen(
    modifier: Modifier = Modifier,
    navigateTo: (HomeScreens) -> Unit = {}
) {
    // Datos simulados de habitaciones
    val mockRooms = listOf(
        Room(
            id = "1",
            name = "Habitación Deluxe",
            precio = 120.0f,
            description = "Amplia habitación con vista al mar y comodidades modernas.",
            capacidad = 2,
            photo = R.drawable.cuarto1.toString() // Reemplazar con tu recurso de imagen
        ),
        Room(
            id = "2",
            name = "Suite Familiar",
            precio = 120.0f,
            description = "Espaciosa suite ideal para familias, con cocina incluida.",
            capacidad = 4,
            photo = R.drawable.cuarto2.toString() // Reemplazar con tu recurso de imagen
        ),
        Room(
            id = "3",
            name = "Estudio Económico",
            precio =  80.0f,
            description =  "Habitación compacta y acogedora para viajeros solitarios.",
            capacidad = 1,
            photo = R.drawable.cuarto3.toString() // Reemplazar con tu recurso de imagen
        ),
    )

    // Control de desplazamiento para el encabezado
    val scrollState = rememberScrollState()

    // Selección de imagen según el tema
    val image = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.hav2m)
    } else {
        painterResource(R.drawable.hav1m)
    }

    // Degradado de imagen
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0x802A2A2A)
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        // Encabezado como item
        item {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = "Encuentra su habitación perfecta",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            // Barra de búsqueda
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                value = "",
                onValueChange = { /* TODO: Implementar búsqueda */ },
                textStyle = MaterialTheme.typography.bodyLarge,
                label = {
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Buscar por ciudad o fecha"
                    )
                },
                placeholder = {
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        text = "Ej: La Habana, 20/09/2025"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icono de búsqueda",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            )
            // Filtros
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(listOf("Todos", "Disponibles")) { category ->
                    FilterChip(
                        selected = false,
                        onClick = { /* TODO: Filtrar por categoría */ },
                        label = {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            labelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
            // Título de la lista
            Text(
                text = "Habitaciones Disponibles",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        // Lista de habitaciones
        items(mockRooms) { room ->
            RoomCard(
                room = room,
                onClick = {
                    navigateTo(InternalHome)
                }
            )
        }
        // Espaciador final
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
