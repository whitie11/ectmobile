package net.whiteapps.ectmobileapp.referral.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.common.domain.enums.Stage

@Serializable
data class ReferralRef(
    val referralId: Int,
    val serviceUserId: Int,
    val firstName: String,
    val midName: String,
    val lastName: String,
    val nhsNo: String,
    val stage: Stage,
    val reason: String,
    @Contextual
    val dateReferred: LocalDate
)

