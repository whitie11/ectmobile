package net.whiteapps.ectmobileapp.referral.data.remote

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
import net.whiteapps.ectmobileapp.referral.data.dto.NewReferralDTO
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.models.Referral
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult

class ReferralAPI(
    private val httpClient: HttpClient,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun getAllOpenReferralRef(): AppResult<ReferralRefResponse> {
        val accessToken = localSettingsRepository.accessToken
        val response = try {
            httpClient.get(
                urlString = "referrals/get_all_open"
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
                val res = response.body<ReferralRefResponse>()
                AppResult.Success(res)
            }

            401 -> AppResult.Error(AppErrorCodes.INVALID_USER, "Invalid User")
            409 -> AppResult.Error(AppErrorCodes.APP_EXISTS, "error 409")
            408 -> AppResult.Error(AppErrorCodes.TIMED_OUT, "error 408, Timed out")
            413 -> AppResult.Error(AppErrorCodes.NETWORK_ERROR,  response.body<ResponseError>().message)
            in 500..599 -> AppResult.Error(AppErrorCodes.UNKNOWN, "Server Error")
            else -> {
                val res = response.body<ResponseError>().message
                AppResult.Error(AppErrorCodes.UNKNOWN, res)
            }
        }
    }

    suspend fun getReferral( refId: Int): AppResult<ReferralServicerUserResponseDTO> {
        val response = try {
            httpClient.get(
                urlString = "referrals/${refId}"
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
                val res = response.body<ReferralServicerUserResponseDTO>()
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

     suspend fun getOpenReferral(suId: Int): AppResult<ReferralServicerUserResponseDTO> {
        val response = try {
            httpClient.get(
                urlString = "referrals/get_open_su/${suId}"
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
                val res = response.body<ReferralServicerUserResponseDTO>()
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

    suspend fun saveReferral( referral: NewReferralDTO) : AppResult<Referral> {
        val response = try {
            httpClient.post(
                urlString = "referrals/add"
            ) {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(referral)
            }
        } catch (e: UnresolvedAddressException) {
            return AppResult.Error(AppErrorCodes.NETWORK_ERROR, e.message ?: "")
        } catch (e: Exception) {
            return AppResult.Error(AppErrorCodes.UNKNOWN, e.message ?: "")
        }

        return when (response.status.value) {
            in 200..299 -> {
                val res = response.body<Referral>()
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