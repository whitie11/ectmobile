package net.whiteapps.ectmobileapp.referral.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.referral.data.dto.NewReferralDTO
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.models.Referral
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource

class SaveReferralUseCase (
    private val refRepository: ReferralRepository,
) {
    operator fun invoke(ref: NewReferralDTO): Flow<Resource<Referral>> = flow {
        try {
            emit(Resource.Loading())
            when(val res = refRepository.saveReferral(ref)) {
                is AppResult.Error ->   {
                    emit(Resource.Error(res.msg))
                }
                is AppResult.Success<Referral> -> {
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