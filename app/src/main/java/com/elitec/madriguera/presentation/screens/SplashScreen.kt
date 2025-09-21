package com.elitec.madriguera.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.navigation.Login
import com.elitec.madriguera.presentation.navigation.MainScreens
import com.elitec.madriguera.presentation.navigation.Splash
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateTo: (MainScreens) -> Unit
) {
    LaunchedEffect(null) {
        delay(1500)
        navigateTo(Login) // Poner logica de logueo inicial
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.icono_astro_durmiendo),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )
    }
}