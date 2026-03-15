package net.whiteapps.ectmobileapp.referral.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.common.domain.enums.Stage

@Serializable
data class Referral(
    val id: Int,
    val referrer: String,
    val referrersEmail: String,
    val serviceUserId: Int,
    val reason: String,
    val stage: Stage,
    val isOpen: Boolean,
    @Contextual
    val dateReferred: LocalDate,
    @Contextual
    val dateClosed: LocalDate?
)