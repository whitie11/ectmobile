package net.whiteapps.ectmobileapp.serviceUser.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.data.dto.ServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.util.AppErrorCodes
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource


class SaveServiceUserUseCase (
    private val serviceUserRepository: ServiceUserRepository,
) {
    operator fun invoke(su: NewServiceUserDTO): Flow<Resource<ServiceUser>> = flow {
        try {
            emit(Resource.Loading())
            when(val res = serviceUserRepository.saveServiceUser(su)) {
                is AppResult.Error ->   {
                        emit(Resource.Error(res.msg))
                }

                is AppResult.Success<ServiceUser> -> {
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