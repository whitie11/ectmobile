package net.whiteapps.ectmobileapp.referral.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource

class GetReferralUseCase (
    private val refRepository: ReferralRepository,
) {
    operator fun invoke(refId: Int): Flow<Resource<ReferralServicerUserResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            when(val res = refRepository.getReferral(refId)) {
                is AppResult.Error ->   {
                    emit(Resource.Error(res.msg))
                }
                is AppResult.Success<ReferralServicerUserResponseDTO> -> {
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