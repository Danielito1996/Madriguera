package com.elitec.madriguera.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.madriguera.presentation.screens.HelpScreen
import com.elitec.madriguera.presentation.screens.LoginScreen
import com.elitec.madriguera.presentation.screens.RegisterScreen
import com.elitec.madriguera.presentation.screens.SplashScreen

@Composable
fun MainNavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        composable<Splash> {
            SplashScreen(
                modifier = Modifier.fillMaxSize(),
                navigateTo = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable<Login> {
            LoginScreen (
                modifier = Modifier.fillMaxSize(),
                navigateTo = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable<SignUp> {
            RegisterScreen(
                modifier = Modifier.fillMaxSize(),
                navigateTo = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable<Help> {
            HelpScreen(
                modifier = Modifier.fillMaxSize(),
                navigateTo = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable<Home> { backStackEntry ->
            val id = backStackEntry.toRoute<Home>().userId
            MainHomevigationWrapper(
                modifier = Modifier.fillMaxSize(),
                logout = {
                    navController.popBackStack()
                },
                userId = id
            )
        }
    }
}