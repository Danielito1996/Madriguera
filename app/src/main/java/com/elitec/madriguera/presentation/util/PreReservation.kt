package com.elitec.madriguera.presentation.util

import com.elitec.madriguera.domain.entities.types.PreReservationStatus
import io.appwrite.models.Room
import kotlinx.datetime.LocalDate

data class PreReservation (
    val id: String,
    val room: Room,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: PreReservationStatus
)