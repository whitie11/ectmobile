package net.whiteapps.ectmobileapp.referral.data.dto

import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef

@Serializable
data class ReferralRefResponse(
    val referrals: List<ReferralRef>
)
