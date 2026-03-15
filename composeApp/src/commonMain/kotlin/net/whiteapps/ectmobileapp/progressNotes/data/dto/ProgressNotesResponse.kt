package net.whiteapps.ectmobileapp.progressNotes.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class ProgressNotesResponse (
    val id: Int,
    val serviceUserId: Int,
    val referralId: Int,
    val user: String,
    val note: String,
    @Contextual
    val dateCreated: LocalDateTime

)