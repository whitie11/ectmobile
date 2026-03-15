package net.whiteapps.ectmobileapp.serviceUser.data.repository

import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.data.remote.ServiceUserAPI
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.serviceUser.data.dto.ServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
class ServiceUserRepositoryImpl(
    private val api: ServiceUserAPI
): ServiceUserRepository {
    override suspend fun getServiceUserByNHSno(nhsNo: String): AppResult<ServiceUserDTO> {
        return api.getServiceUserByNHS(nhsNo)
    }

    override suspend fun saveServiceUser(serviceUser: NewServiceUserDTO): AppResult<ServiceUser>{
        return api.saveServiceUser(serviceUser)
    }

}