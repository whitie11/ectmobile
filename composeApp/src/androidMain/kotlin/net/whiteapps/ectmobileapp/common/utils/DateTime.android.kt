package net.whiteapps.ectmobileapp.common.utils
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

actual object DateFormatter {
    @JvmStatic
    actual fun getFormattedDate(
        timestamp: String,
        format: String,
        outputFormat: String
    ): String {
        val timestampFormat = format
        val outputFormat = outputFormat

        val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

        val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("GMT")

        try {
            val date = parser.parse(timestamp)
            if (date != null) {
                dateFormatter.timeZone = TimeZone.getDefault()
                return dateFormatter.format(date)
            }
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original timestamp
        return timestamp
    }

}