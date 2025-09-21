package com.elitec.madriguera.presentation.navigation

import kotlinx.serialization.Serializable
import java.io.Serial

interface MainScreens

@Serializable
object Splash: MainScreens

@Serializable
object Login: MainScreens

@Serializable
object SignUp: MainScreens

@Serializable
object Help: MainScreens

@Serializable
data class Home(
    val userId: String
): MainScreens
