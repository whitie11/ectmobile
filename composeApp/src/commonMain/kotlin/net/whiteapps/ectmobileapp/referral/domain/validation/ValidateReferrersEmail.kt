package net.whiteapps.ectmobileapp.referral.domain.validation

import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateReferrersEmail {
    fun execute(inputText: String): ValidationResult {
        val emailAddressRegex = Regex(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        if (inputText.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email address cannot be blank"
            )
        }
        if(!inputText.matches(emailAddressRegex)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email address is invalid"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}