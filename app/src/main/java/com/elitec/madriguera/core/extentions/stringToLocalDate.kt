package com.elitec.madriguera.core.extentions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDate(pattern: String? = "dd/MM/yyyy"): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, formatter)
}

fun String.toLocalDate1(pattern: String? = "dd/MM/yyyy"): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        LocalDate.parse(this, formatter)
    } catch (e: java.time.format.DateTimeParseException) {
        null // O manejar el error según tu lógica
    }
}