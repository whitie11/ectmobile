package net.whiteapps.ectmobileapp.common.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.RefreshTokenDTO
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository
import net.whiteapps.ectmobileapp.util.HttpStatusCodeSerializer


expect fun provideHttpClient(
    localSettingsRepository: LocalSettingsRepository
): HttpClient

fun commonHttpClient(
    localSettingsRepository: LocalSettingsRepository,
    engine: HttpClientEngineFactory<*>,
): HttpClient {
    return HttpClient(engine) {
        defaultRequest {
            url("http://86.161.46.116:8080/")
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
                serializersModule = SerializersModule {
                    contextual(HttpStatusCode::class) { HttpStatusCodeSerializer }
                }
            })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = localSettingsRepository.accessToken
                    val refreshToken = localSettingsRepository.refreshToken
                    // Load tokens from a local storage and return them as the 'BearerTokens' instance
                    BearerTokens(accessToken, refreshToken)
                }
                refreshTokens {
                    val refreshToken = localSettingsRepository.refreshToken
                    val refreshDTO = RefreshTokenDTO(
                        refreshToken = refreshToken
                    )
                    val newTokens: AuthResponse = client.post(
                        urlString = "auth/refreshToken"
                    ) {
                        accept(ContentType.Application.Json)
                        contentType(ContentType.Application.Json)
                        setBody(refreshDTO)
                    }.body()
                    localSettingsRepository.accessToken = newTokens.accessToken
                    localSettingsRepository.refreshToken = newTokens.refreshToken
                    BearerTokens(newTokens.accessToken, newTokens.refreshToken)
                }
            }
        }
//        install(Logging) {
//            level = LogLevel.BODY
//        }

    }
}