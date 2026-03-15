package net.whiteapps.ectmobileapp.auth.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.ResponseError
import net.whiteapps.ectmobileapp.auth.data.dto.TokenRequestDTO
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun getTokens(tr: TokenRequestDTO): AppResult<AuthResponse> {
        val response = try {
           httpClient.post(
               urlString = "auth/login"
           ){
               contentType(ContentType.Application.Json)
               setBody(tr)
           }
        }catch(e: UnresolvedAddressException) {
            return AppResult.Error(AppErrorCodes.NETWORK_ERROR, e.message?: "")
        } catch(e: Exception) {
            return AppResult.Error(AppErrorCodes.UNKNOWN, e.message?: "")
        }

        return when(response.status.value) {
            in 200..299 -> {
                val res = response.body<AuthResponse>()
                AppResult.Success(res)
            }
            401 -> AppResult.Error(AppErrorCodes.INVALID_USER, "Invalid User" )
            409 -> AppResult.Error(AppErrorCodes.APP_EXISTS, "error 409")
            408 -> AppResult.Error(AppErrorCodes.TIMED_OUT, "Network Timed out")
            413 -> AppResult.Error(AppErrorCodes.NETWORK_ERROR, "Payload too large")
            in 500..599 -> AppResult.Error(AppErrorCodes.UNKNOWN, "Server Error")
            else -> {
               val  res = tr.name + ": " + response.body<ResponseError>().message
                AppResult.Error(AppErrorCodes.UNKNOWN, res )
            }
        }

    }

//    suspend fun refreshToken(token: String): AuthResponse {
//        val response =
//            httpClient.post(
//                urlString = "auth/refreshToken"
//            ){
//                contentType(ContentType.Application.Json)
//                setBody(token)
//            }


//return response.body()
//        return when(response.status.value) {
//            in 200..299 -> {
//                val res = response.body<AuthResponse>()
//                AppResult.Success(res)
//            }
//            401 -> AppResult.Error(AppErrorCodes.INVALID_USER, "Invalid User" )
//            409 -> AppResult.Error(AppErrorCodes.APP_EXISTS, "error 409")
//            408 -> AppResult.Error(AppErrorCodes.TIMED_OUT, "Network Timed out")
//            413 -> AppResult.Error(AppErrorCodes.NETWORK_ERROR, "Payload too large")
//            in 500..599 -> AppResult.Error(AppErrorCodes.UNKNOWN, "Server Error")
//            else -> {
//                val  errorMsg = response.body<ResponseError>().message
//                AppResult.Error(AppErrorCodes.UNKNOWN, errorMsg )
//            }
//        }

//    }
//    suspend fun getUser(userId: Int, token: String): Result<UserItemDTO, NetworkError> {
//        val response = try {
//            httpClient.get(
////                urlString = "https://pool.whiteapps.net/user/getUser/"
//                        urlString = "http://192.168.1.113:8000/user/getUser/"
//            ){
////                headers {
////                    append("Authorization", "Bearer $token")
////                }
//                url {
//                    parameters.append("userId", userId.toString())
//                }
//                contentType(ContentType.Application.Json)
//                accept(ContentType.Application.Json)
//             bearerAuth(token)
//            }
//        }catch(e: UnresolvedAddressException) {
//            return Result.Error(NetworkError.NO_INTERNET)
//        } catch(e: Exception) {
//            val message = e.message
//            val m= message + "!!"
//            return Result.Error(NetworkError.UNKNOWN)
//        }
//
//        return when(response.status.value) {
//            in 200..299 -> {
//                val res = response.body<UserItemDTO>()
//                Result.Success(res)
//            }
//            401 -> Result.Error(NetworkError.UNAUTHORIZED)
//            409 -> Result.Error(NetworkError.CONFLICT)
//            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
//            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
//            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
//            else -> Result.Error(NetworkError.UNKNOWN)
//        }
//
//    }
//


}