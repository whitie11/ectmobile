package net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.serviceUser.domain.use_case.GetServiceUserUseCase
import net.whiteapps.ectmobileapp.util.Resource

class SelectServiceUserViewModel(
    private val serviceUserRepository: ServiceUserRepository
): ViewModel() {


    private val _suNetworkStatue = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val suNetworkStatue = _suNetworkStatue.asStateFlow()

    private val _serviceUser = MutableStateFlow<ServiceUser?>(null)
    val serviceUser = _serviceUser.asStateFlow()


    val getServiceUserUseCase: GetServiceUserUseCase = GetServiceUserUseCase(
        serviceUserRepository = serviceUserRepository,
    )

    fun resetNetworkStatus(){
        _suNetworkStatue.value = NetworkStatus.Idle
    }

    fun getServiceUserByNhs(nhsNo: String) {
        getServiceUserUseCase(nhsNo).onEach { result: Resource<ServiceUser> ->
            when (result) {
                is Resource.Success -> {
                    _suNetworkStatue.value = NetworkStatus.Success
                    result.data?.let { _serviceUser.value = it}
                val x = _serviceUser.value
                }

                is Resource.Error -> {
                    _suNetworkStatue.value = NetworkStatus.Failure

                }

                is Resource.Loading -> {
                    _suNetworkStatue.value = NetworkStatus.Loading

                }

                is Resource.Nothing -> {
                    _suNetworkStatue.value = NetworkStatus.NotFound

                }
            }
        }.launchIn(viewModelScope)
    }


}
