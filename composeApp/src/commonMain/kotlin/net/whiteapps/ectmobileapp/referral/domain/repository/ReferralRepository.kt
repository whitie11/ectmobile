package net.whiteapps.ectmobileapp.referral.domain.repository

import net.whiteapps.ectmobileapp.referral.data.dto.NewReferralDTO
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.models.Referral
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.util.AppResult

interface ReferralRepository {
    suspend fun getAllOpenReferralRef(): AppResult<ReferralRefResponse>

    suspend fun getReferral(refId: Int): AppResult<ReferralServicerUserResponseDTO>

    suspend fun getOpenReferral(suId: Int): AppResult<ReferralServicerUserResponseDTO>

    suspend fun saveReferral(referral: NewReferralDTO): AppResult<Referral>
}