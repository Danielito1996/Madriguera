package com.elitec.madriguera.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elitec.madriguera.presentation.screens.homeInternal.InternalHomeScreen

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
                        navController.navigate(InternalHome)
                    }
                )
            }
        }
    }
}