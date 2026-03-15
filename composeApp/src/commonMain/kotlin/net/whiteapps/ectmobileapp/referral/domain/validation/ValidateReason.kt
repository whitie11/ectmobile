package net.whiteapps.ectmobileapp.referral.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateReason {
    fun execute(inputText: String): ValidationResult {
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please provide a reason for this referral"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}