package ru.otus.dialogs.service.event

import com.fasterxml.jackson.annotation.JsonAutoDetect
import java.time.Instant
import java.util.UUID

@JsonAutoDetect
data class SnoDialogEvent(

    val id: UUID? = null,
    val from: String,
    val to: String,
    val text: String,
    val timeModified: Instant? = null,
    val operation: String, //TODO or STRING ?

)
