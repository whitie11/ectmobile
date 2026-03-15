package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateLastname {
    fun execute(inputText: String): ValidationResult {
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The last name can't be empty"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
