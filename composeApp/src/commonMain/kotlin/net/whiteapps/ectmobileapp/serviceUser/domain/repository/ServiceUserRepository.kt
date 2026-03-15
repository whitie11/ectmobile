package net.whiteapps.ectmobileapp.serviceUser.domain.repository

import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.data.dto.ServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.util.AppResult

interface ServiceUserRepository {
    suspend fun getServiceUserByNHSno(nhsNo: String): AppResult<ServiceUserDTO>
    suspend fun saveServiceUser(serviceUser: NewServiceUserDTO): AppResult<ServiceUser>
}
