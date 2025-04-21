package ru.otus.dialogs.service.event

import java.time.Instant
import java.util.*

data class SnoDialogEvent(

    val id: UUID? = null,
    val from: String,
    val to: String,
    val text: String,
    val timeModified: Instant? = null,
    val operation: String, //TODO or STRING ?

)
