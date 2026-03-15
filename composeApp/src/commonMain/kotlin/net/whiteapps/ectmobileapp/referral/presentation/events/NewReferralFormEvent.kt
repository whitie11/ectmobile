package net.whiteapps.ectmobileapp.referral.presentation.events

import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.enums.Gender

sealed class NewReferralFormEvent {
    data class ServiceUserChanged(val serviceUser: ServiceUser?) : NewReferralFormEvent()
    data class ReferrerChanged(val referrer: String) : NewReferralFormEvent()
    data class ReferrersEmailChanged(val referrersEmail: String) : NewReferralFormEvent()
    data class ReasonChanged(val reason: String) : NewReferralFormEvent()
    data object Submit : NewReferralFormEvent()
}