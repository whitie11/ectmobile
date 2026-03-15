package net.whiteapps.ectmobileapp.progressNotes.domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ProgressNote(
    val id: Int,
    val serviceUserId: Int,
    val referralId: Int,
    val note: String,
    @Contextual
    val dateCreated: LocalDateTime

)