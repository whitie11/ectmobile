package net.whiteapps.ectmobileapp.serviceUser.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.serviceUser.data.dto.ServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource

class GetServiceUserUseCase (
    private val serviceUserRepository: ServiceUserRepository,
) {
    operator fun invoke(nhsNo: String): Flow<Resource<ServiceUser>> = flow {
        try {
            emit(Resource.Loading())
            when(val res = serviceUserRepository.getServiceUserByNHSno(nhsNo)) {
                is AppResult.Error ->   {
                    if (res.error == AppErrorCodes.UNKNOWN_NHS) {
                        emit(Resource.Nothing<ServiceUser>())
                    } else
                    emit(Resource.Error(res.msg))
                }
                
                is AppResult.Success<ServiceUserDTO> -> {
                    val it = res.data.serviceUser
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