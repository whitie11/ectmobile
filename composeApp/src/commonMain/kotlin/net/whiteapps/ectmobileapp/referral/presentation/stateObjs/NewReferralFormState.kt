package net.whiteapps.ectmobileapp.referral.presentation.stateObjs

import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.enums.Gender

data class NewReferralFormState(
    val serviceUser: ServiceUser? = null,
    val serviceUserError: String? = null,
    val referrer: String = "",
    val referrerError: String? = null,
    val referrersEmail: String = "",
    val referrersEmailError: String? = null,
    val reason: String = "",
    val reasonError: String? = null,
    )