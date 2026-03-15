package net.whiteapps.ectmobileapp.referral.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateReferrer {
    fun execute(inputText: String): ValidationResult {
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter a referrer's name"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}