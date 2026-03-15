package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateMidname {
    fun execute(inputText: String): ValidationResult {
        return ValidationResult(
            successful = true
        )
    }
}
