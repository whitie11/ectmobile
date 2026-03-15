package net.whiteapps.ectmobileapp.referral.data.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.common.domain.enums.Stage
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser


@Serializable
data class ReferralServicerUserResponseDTO(
    val id: Int,
    val referrer: String,
    val referrersEmail: String,
    val serviceUserId: Int,
    val serviceUser: ServiceUser,
    val reason: String,
    val stage: Stage,
    val isOpen: Boolean,
    @Contextual
    val dateReferred: LocalDate,
    @Contextual
    val dateClosed: LocalDate?
)