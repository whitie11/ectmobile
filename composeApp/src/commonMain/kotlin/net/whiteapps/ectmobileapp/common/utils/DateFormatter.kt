package net.whiteapps.ectmobileapp.common.utils

expect object DateFormatter {
    fun getFormattedDate(
        timestamp: String,
        format: String = "yyyy-MM-dd",
        outputFormat: String = "dd/MM/yy"
    ): String

}