package com.elitec.madriguera.presentation.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.screens.homeInternal.InternalHomeScreen
import com.elitec.madriguera.presentation.screens.homeInternal.PaymentsScreen
import com.elitec.madriguera.presentation.screens.homeInternal.PreReservationsScreen
import com.elitec.madriguera.presentation.screens.homeInternal.ReservationScreen
import io.appwrite.models.Room

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomevigationWrapper(
    modifier: Modifier = Modifier,
    logout: () -> Unit,
    userId: String
) {

    val navController = rememberNavController()

    val options = listOf("United States", "Greece", "Indonesia", "United Kingdom")
    var selected by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            Row {
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                DropdownMenu(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    shadowElevation = 5.dp,
                    tonalElevation = 3.dp,
                    shape = RoundedCornerShape(
                        topEnd = 15.dp,
                        bottomEnd = 15.dp
                    ),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Cerrar sesión",
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        onClick = {
                            logout()
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Sus reservaciones",
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        onClick = {
                            expanded = false
                            navController.navigate(PreReservation(userId))
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.DateRange,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    )
                }
            }

        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = InternalHome,
            modifier = Modifier.fillMaxSize().padding(contentPadding)
        ) {
            composable<InternalHome> {
                InternalHomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateTo = { screen ->
                        navController.navigate(screen)
                    }
                )
            }
            composable<Reservation> { backStackEntry ->
                val roomId = backStackEntry.toRoute<Reservation>().roomId
                val ex = Room(
                    id = "1",
                    name = "Habitación Deluxe",
                    precio = 120.0f,
                    description = "Amplia habitación con vista al mar y comodidades modernas.",
                    capacidad = 2,
                    photo = R.drawable.cuarto1.toString() // Reemplazar con tu recurso de imagen
                )
                ReservationScreen(
                    room = ex,
                    modifier = Modifier.fillMaxSize(),
                    navigateTo = { screen ->
                        navController.navigate(screen)
                    },
                    onConfirmReservation = { init, final ->
                        navController.navigate(
                            Payments(
                                roomId = roomId,
                                initDate = init.toString(),
                                endDate = final.toString()
                            )
                        )
                    }
                )
            }
            composable<Payments> { backStackEntry ->
                val parameters = backStackEntry.toRoute<Payments>()
                val ex = Room(
                    id = "1",
                    name = "Habitación Deluxe",
                    precio = 120.0f,
                    description = "Amplia habitación con vista al mar y comodidades modernas.",
                    capacidad = 2,
                    photo = R.drawable.cuarto1.toString() // Reemplazar con tu recurso de imagen
                )
                PaymentsScreen(
                    startDate = parameters.initDate,
                    endDate = parameters.endDate,
                    room = ex,
                    onConfirmPayment = { method, mobileNumber ->
                        navController.navigate(InternalHome)
                    },
                    navigateTo = { screen ->
                        navController.navigate(InternalHome)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable<PreReservation> { backStackEntry ->

                PreReservationsScreen(
                    userId = userId,
                    navigateTo = { screen ->
                        navController.navigate(screen)
                    },
                    modifier = Modifier.fillMaxSize(),
                    onConfirmPreReservation = { init, end ->
                    }
                )
            }
        }
    }
}