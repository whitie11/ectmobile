package net.whiteapps.ectmobileapp.referral.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.referral.domain.use_case.GetOpenReferralUseCase
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser


class ValidateServiceUser(
    private val refRepository: ReferralRepository,
) {

    val getOpenReferralUseCase: GetOpenReferralUseCase = GetOpenReferralUseCase(
        refRepository = refRepository,
    )


    fun execute(su: ServiceUser?): ValidationResult {
        if (su == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select a service user"
            )
        }

        return ValidationResult(
            successful = true
        )
    }



}