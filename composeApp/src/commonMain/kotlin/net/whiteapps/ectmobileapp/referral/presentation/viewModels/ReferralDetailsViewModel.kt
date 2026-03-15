package net.whiteapps.ectmobileapp.referral.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.referral.domain.use_case.GetReferralUseCase
import net.whiteapps.ectmobileapp.util.Resource

class ReferralDetailsViewModel(
    private val refRepository: ReferralRepository
) : ViewModel()  {

    val getReferralUseCase: GetReferralUseCase = GetReferralUseCase(
        refRepository = refRepository,
    )

    private val _selectedRef = MutableStateFlow<ReferralServicerUserResponseDTO?>(null)
    val selectedRef = _selectedRef.asStateFlow()

    private val _refNetworkStatue = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val refNetworkStatue = _refNetworkStatue.asStateFlow()

    private val _errorMsg = MutableStateFlow("")
    val errorMsg = _errorMsg.asStateFlow()

    fun getReferral(refId: Int) {
        getReferralUseCase(refId).onEach { result: Resource<ReferralServicerUserResponseDTO> ->
            when (result) {
                is Resource.Success -> {
                    _refNetworkStatue.value = NetworkStatus.Success
                    result.data?.let { _selectedRef.value = it }
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

                is Resource.Nothing<*> -> {
                    _refNetworkStatue.value = NetworkStatus.NotFound
                    _errorMsg.value = result.message ?: "Referral not found!"
                }
            }
        }.launchIn(viewModelScope)
    }
}