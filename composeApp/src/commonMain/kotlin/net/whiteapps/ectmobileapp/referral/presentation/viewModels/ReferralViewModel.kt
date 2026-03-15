package net.whiteapps.ectmobileapp.referral.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.referral.domain.use_case.GetAllOpenReferralRef
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.referral.domain.use_case.GetOpenReferralUseCase
import net.whiteapps.ectmobileapp.util.Resource

class ReferralViewModel(
    private val refRepository: ReferralRepository
) : ViewModel() {
    val screenTitle = "ECT Referrals"

    private val _refNetworkStatue = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val refNetworkStatue = _refNetworkStatue.asStateFlow()

    private val _errorMsg = MutableStateFlow("")
    val errorMsg = _errorMsg.asStateFlow()

    val getAllOpenReferralRefUseCase: GetAllOpenReferralRef = GetAllOpenReferralRef(
        refRepository = refRepository
    )

    val getOpenReferralUseCase: GetOpenReferralUseCase = GetOpenReferralUseCase(
        refRepository = refRepository,
    )

    private val _referralRefList = MutableStateFlow<List<ReferralRef>>(emptyList())
    val referralRefList = _referralRefList.asStateFlow()

//    private val _selectedRef = MutableStateFlow<ReferralServicerUserResponseDTO?>(null)
//    val selectedRef = _selectedRef.asStateFlow()

     fun getAllOpenReferralRef() {
        getAllOpenReferralRefUseCase().onEach { result: Resource<ReferralRefResponse> ->
            when (result) {
                is Resource.Success -> {
                    _refNetworkStatue.value = NetworkStatus.Success
                    result.data?.let { _referralRefList.value = it.referrals }
                    _errorMsg.value = ""
                }

                is Resource.Error -> {
                    _refNetworkStatue.value = NetworkStatus.Failure
                    _errorMsg.value = result.message ?: "An unexpected error occurred"
                }

                is Resource.Loading -> {
                    _refNetworkStatue.value = NetworkStatus.Loading
                    _errorMsg.value = ""
                }

                is Resource.Nothing -> {
                    _refNetworkStatue.value = NetworkStatus.NotFound
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getOpenReferral(suId: Int) {
        getOpenReferralUseCase(suId).onEach { result: Resource<ReferralServicerUserResponseDTO> ->
            when (result) {
                is Resource.Success -> {
//                    _refNetworkStatue.value = NetworkStatus.Success
//                    result.data?.let { _selectedRef.value = it }
//                    _errorMsg.value = ""
                }
                is Resource.Error -> {
//                    _refNetworkStatue.value = NetworkStatus.Failure
//                    _errorMsg.value = result.message ?: "An unexpected error occurred"
                }

                is Resource.Loading -> {
//                    _refNetworkStatue.value = NetworkStatus.Loading
//                    _errorMsg.value = ""
                }

                is Resource.Nothing<*> -> {}

            }
        }.launchIn(viewModelScope)
    }

}