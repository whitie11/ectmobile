package net.whiteapps.ectmobileapp.auth.data.repository

import auth.domain.repository.AuthRepository
import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.TokenRequestDTO
import net.whiteapps.ectmobileapp.auth.data.remote.AuthApi
import net.whiteapps.ectmobileapp.util.AppResult


class AuthRepositoryImpl(
    private val api: AuthApi,
) : AuthRepository {
    override suspend fun getToken(tr: TokenRequestDTO): AppResult<AuthResponse> {
        return api.getTokens(tr)
    }


}