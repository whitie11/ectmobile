package net.whiteapps.ectmobileapp.common.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository

actual fun provideHttpClient(
    localSettingsRepository: LocalSettingsRepository
): HttpClient = commonHttpClient(
    localSettingsRepository,
    OkHttp
)