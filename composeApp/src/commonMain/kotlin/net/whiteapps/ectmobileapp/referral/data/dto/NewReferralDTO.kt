package net.whiteapps.ectmobileapp.referral.data.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.whiteapps.enums.Gender

@Serializable
data class NewReferralDTO(
    val serviceUserId: Int,
    val referrer: String,
    val referrersEmail: String,
    val reason: String,
)