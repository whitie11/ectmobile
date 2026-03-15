package net.whiteapps.ectmobileapp.common.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository


actual fun provideHttpClient(
    localSettingsRepository: LocalSettingsRepository
): HttpClient = commonHttpClient(
    localSettingsRepository,Darwin)
