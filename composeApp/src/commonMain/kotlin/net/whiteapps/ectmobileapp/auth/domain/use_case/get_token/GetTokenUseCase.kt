package net.whiteapps.ectmobileapp.auth.domain.use_case.get_token

import auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.auth.data.dto.AuthResponse
import net.whiteapps.ectmobileapp.auth.data.dto.TokenRequestDTO
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource


class GetTokenUseCase(
    private val authRepository: AuthRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {
    operator fun invoke(tr: TokenRequestDTO): Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
           when(val res = authRepository.getToken(tr)) {
               is AppResult.Error ->   {
                   emit(Resource.Error(res.msg))
               }
               is AppResult.Success<AuthResponse> -> {
                   localSettingsRepository.accessToken = res.data.accessToken
                   localSettingsRepository.refreshToken = res.data.refreshToken
                   val it = res.data
                   emit(Resource.Success(it))
               }
           }
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch(e: Throwable ) {
            emit(Resource.Error(e.message?: "Something went badly wrong!!"))
        }

    }
}