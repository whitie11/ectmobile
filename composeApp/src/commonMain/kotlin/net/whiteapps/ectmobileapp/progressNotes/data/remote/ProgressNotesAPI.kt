package net.whiteapps.ectmobileapp.progressNotes.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import net.whiteapps.ectmobileapp.auth.data.dto.ResponseError
import net.whiteapps.ectmobileapp.progressNotes.data.dto.ProgressNotesResponse
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult

class ProgressNotesAPI(
    private val httpClient: HttpClient
) {
    suspend fun getProgressNotesReferral(refId: Int): AppResult<List<ProgressNotesResponse>> {
        val response = try {
            httpClient.get(
                urlString =  "progress_notes_referral/${refId}"
            ) {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException) {
            return AppResult.Error(AppErrorCodes.NETWORK_ERROR, e.message ?: "")
        } catch (e: Exception) {
            return AppResult.Error(AppErrorCodes.UNKNOWN, e.message ?: "")
        }

        return when (response.status.value) {
            in 200..299 -> {
                val res = response.body<List<ProgressNotesResponse>>()
                AppResult.Success(res)
            }

            401 -> AppResult.Error(AppErrorCodes.INVALID_USER, "Invalid User")
            409 -> AppResult.Error(AppErrorCodes.APP_EXISTS, "error 409")
            408 -> AppResult.Error(AppErrorCodes.TIMED_OUT, "error 408, Timed out")
            413 -> AppResult.Error(AppErrorCodes.NETWORK_ERROR, "Payload too large")
            in 500..599 -> AppResult.Error(AppErrorCodes.UNKNOWN, "Server Error")
            else -> {
                val res = response.body<ResponseError>().message
                AppResult.Error(AppErrorCodes.UNKNOWN, res)
            }
        }
    }
}