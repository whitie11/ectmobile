package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult
import net.whiteapps.enums.Gender

class ValidateGender {
    fun execute(gender: Gender?): ValidationResult {
        if (gender == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select gender"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
