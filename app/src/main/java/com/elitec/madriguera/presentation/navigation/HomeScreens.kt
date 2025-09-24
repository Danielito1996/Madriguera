package com.elitec.madriguera.presentation.navigation

import kotlinx.serialization.Serializable
import java.sql.Date

interface HomeScreens

@Serializable
object InternalHome: HomeScreens

@Serializable
data class Reservation(
    val roomId: String
): HomeScreens

@Serializable
data class Payments(
    val roomId: String,
    val initDate: String,
    val endDate: String
): HomeScreens

@Serializable
data class PreReservation(
    val userId: String
): HomeScreens


