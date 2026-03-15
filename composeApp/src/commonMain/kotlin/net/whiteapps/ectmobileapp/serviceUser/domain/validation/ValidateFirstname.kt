package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateFirstname {
    fun execute(inputText: String): ValidationResult {
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The first name can't be empty"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
