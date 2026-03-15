package net.whiteapps.ectmobileapp.common.domain.repository

import net.whiteapps.ectmobileapp.common.domain.models.IsLoggedIn
import kotlinx.coroutines.flow.StateFlow

interface LocalSettingsRepository {
    var accessToken: String
    var refreshToken: String


    fun  isLoggedIn() : StateFlow<IsLoggedIn>

    fun setIsLoggedInState(isLoggedIn: IsLoggedIn)
}