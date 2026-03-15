package net.whiteapps.ectmobileapp.util

import kotlinx.serialization.Serializable

/**
 * Generic results class that's returned in DAO calls and Validation
 * checks.
 */
@Serializable
sealed class AppResult<out T> {
    @Serializable
    data class Success<out T>(val data: T) : AppResult<T>()
    @Serializable
    data class Error(val error: AppErrorCodes, val msg: String = "") : AppResult<Nothing>()
}
