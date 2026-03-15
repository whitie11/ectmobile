package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateNHSno {
    fun execute(inputText: String): ValidationResult {
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The NHS number can't be empty"
            )
        }
        if (inputText.length != 10) {
            return ValidationResult(
                successful = false,
                errorMessage = "The NHS number must be 10 characters"
            )
        }
        if (!inputText.matches(Regex("^[0-9]*$"))) {
            return ValidationResult(
                successful = false,
                errorMessage = "The NHS number must be only digits"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
