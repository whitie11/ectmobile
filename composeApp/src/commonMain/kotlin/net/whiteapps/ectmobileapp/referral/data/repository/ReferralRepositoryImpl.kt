package net.whiteapps.ectmobileapp.referral.data.repository

import net.whiteapps.ectmobileapp.referral.data.dto.NewReferralDTO
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.data.remote.ReferralAPI
import net.whiteapps.ectmobileapp.referral.domain.models.Referral
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.util.AppResult

class ReferralRepositoryImpl( private val api: ReferralAPI): ReferralRepository {
    override suspend fun getAllOpenReferralRef(): AppResult<ReferralRefResponse> {
       return api.getAllOpenReferralRef()
    }

    override suspend fun getReferral(refId: Int): AppResult<ReferralServicerUserResponseDTO> {
        return api.getReferral(refId)
    }

    override suspend fun getOpenReferral(suId: Int): AppResult<ReferralServicerUserResponseDTO> {
        return api.getOpenReferral(suId)
    }

    override suspend fun saveReferral(referral: NewReferralDTO): AppResult<Referral> {
       return api.saveReferral(referral)
    }


}