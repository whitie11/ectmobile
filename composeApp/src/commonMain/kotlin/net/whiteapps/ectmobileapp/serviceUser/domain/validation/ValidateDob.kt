package net.whiteapps.ectmobileapp.serviceUser.domain.validation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import net.whiteapps.ectmobileapp.common.utils.ValidationResult

class ValidateDob {
    @OptIn(FormatStringsInDatetimeFormats::class)
    fun execute(dateStr: String): ValidationResult {
        if (dateStr.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter a date of birth"
            )
        }
        try {
            LocalDate.parse(
                input =dateStr,
                format = LocalDate.Format { byUnicodePattern("dd.MM.yyyy")})
        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter a valid date in the format dd.MM.yyyy"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
