package auth.domain.repository

import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.TokenRequestDTO
import net.whiteapps.ectmobileapp.util.AppResult


interface AuthRepository {
    suspend fun getToken(tr: TokenRequestDTO): AppResult<AuthResponse>
}

