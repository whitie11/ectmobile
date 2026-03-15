package net.whiteapps.ectmobileapp.serviceUser.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import net.whiteapps.ectmobileapp.auth.data.dto.ResponseError
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.data.dto.ServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult

class ServiceUserAPI(
    private val httpClient: HttpClient,
) {
    suspend fun getServiceUserByNHS( nhsNo: String): AppResult<ServiceUserDTO> {
        val response = try {
            httpClient.get(
                urlString = "service_user_nhs/${nhsNo}"
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
                val res = response.body<ServiceUserDTO>()
                AppResult.Success(res)
            }
            400 -> AppResult.Error(AppErrorCodes.UNKNOWN_NHS, "unknown NHS no")
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

    suspend fun saveServiceUser( serviceUser: NewServiceUserDTO): AppResult<ServiceUser> {
        val response = try {
            httpClient.post(
                urlString = "serviceuseradd"
            ) {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(serviceUser)
            }
        } catch (e: UnresolvedAddressException) {
            return AppResult.Error(AppErrorCodes.NETWORK_ERROR, e.message ?: "")
        } catch (e: Exception) {
            return AppResult.Error(AppErrorCodes.UNKNOWN, e.message ?: "")
        }

        return when (response.status.value) {
            in 200..299 -> {
                val res = response.body<ServiceUser>()
                AppResult.Success(res)
            }
            400 -> AppResult.Error(AppErrorCodes.UNKNOWN, "Bad Request")
            401 -> AppResult.Error(AppErrorCodes.INVALID_USER, "Invalid User")
            409 -> AppResult.Error(AppErrorCodes.USER_EXISTS, "Service User already exists")
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