package net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.common.utils.DateFormatter
import net.whiteapps.ectmobileapp.serviceUser.data.dto.NewServiceUserDTO
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.serviceUser.domain.use_case.SaveServiceUserUseCase
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateDob
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateFirstname
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateGender
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateLastname
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateMidname
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateNHSno
import net.whiteapps.ectmobileapp.serviceUser.presentation.events.NewSUFormEvent
import net.whiteapps.ectmobileapp.serviceUser.presentation.stateObjs.NewSUFormState
import net.whiteapps.ectmobileapp.util.Resource

class NewSUViewModel(
    private val serviceUserRepository: ServiceUserRepository
) : ViewModel() {
    private val validateFirstname: ValidateFirstname = ValidateFirstname()
    private val validateMidname: ValidateMidname = ValidateMidname()
    private val validateLastname: ValidateLastname = ValidateLastname()
    private val validateDob: ValidateDob = ValidateDob()
    private val validateNHSno: ValidateNHSno = ValidateNHSno()
    private val validateGender: ValidateGender = ValidateGender()

    var state by mutableStateOf(NewSUFormState())

    val saveServiceUserUseCase: SaveServiceUserUseCase = SaveServiceUserUseCase(
        serviceUserRepository = serviceUserRepository,
    )

    private val _newSuNetworkStatue = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val newSuNetworkStatue = _newSuNetworkStatue.asStateFlow()

    private val _serviceUser = MutableStateFlow<ServiceUser?>(null)
    val serviceUser = _serviceUser.asStateFlow()

    private val _canSubmit = MutableStateFlow<Boolean>(false)
    val canSubmit = _canSubmit.asStateFlow()

    fun onEvent(event: NewSUFormEvent) {
        when (event) {
            is NewSUFormEvent.DobChanged -> {
                state = state.copy(dob = event.dob)
            }

            is NewSUFormEvent.FirstnameChanged -> {
                state = state.copy(firstname = event.firstname)
            }

            is NewSUFormEvent.GenderChanged -> {
                state = state.copy(gender = event.gender)
            }

            is NewSUFormEvent.LastnameChanged -> {
                state = state.copy(lastname = event.lastname)
            }

            is NewSUFormEvent.MidnameChanged -> {
                state = state.copy(midname = event.midname)
            }

            is NewSUFormEvent.NHSnoChanged -> {
                state = state.copy(nhsNo = event.nhsNo)
            }

            is NewSUFormEvent.Submit -> {
                submitData()
            }

        }
        setCanSubmit()
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    private fun setCanSubmit() {
       _canSubmit.value =  state.firstname.isNotBlank() &&
                state.lastname.isNotBlank() &&
                state.dob.isNotBlank() &&
                state.nhsNo.isNotBlank() &&
                (state.gender != null)
    }

    private fun submitData() {
        val firstnameResult = validateFirstname.execute(state.firstname)
        val midnameResult = validateMidname.execute(state.midname)
        val lastnameResult = validateLastname.execute(state.lastname)
        val dobResult = validateDob.execute(state.dob)
        val nhsNoResult = validateNHSno.execute(state.nhsNo)
        val genderResult = validateGender.execute(state.gender)

        val hasError = listOf(
            firstnameResult,
            midnameResult,
            lastnameResult,
            dobResult,
            nhsNoResult,
            genderResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                firstnameError = firstnameResult.errorMessage,
                lastnameError = lastnameResult.errorMessage,
                midnameError = midnameResult.errorMessage,
                dobError = dobResult.errorMessage,
                nhsNoError = nhsNoResult.errorMessage,
                genderError = genderResult.errorMessage,
            )
            return
        }
        val formattedDateStr = DateFormatter.getFormattedDate(state.dob, "dd.MM.yyyy", "yyyy-MM-dd")
        val newSU = NewServiceUserDTO(
            firstname = state.firstname,
            lastname = state.lastname,
            midname = state.midname,
            dob = LocalDate.parse(formattedDateStr),
            nhsNo = state.nhsNo,
            gender = state.gender,
        )
        saveServiceUser(newSU)
    }

    fun saveServiceUser(newSU: NewServiceUserDTO) {
        saveServiceUserUseCase(newSU).onEach { result: Resource<ServiceUser> ->
            when (result) {
                is Resource.Success -> {
                    _newSuNetworkStatue.value = NetworkStatus.Success
                    result.data?.let { _serviceUser.value = it}
                }

                is Resource.Error -> {
                    _newSuNetworkStatue.value = NetworkStatus.Failure
                    val y = result
                }

                is Resource.Loading -> {
                    _newSuNetworkStatue.value = NetworkStatus.Loading
                    val z = result
                }

                is Resource.Nothing -> {
//                    _suNetworkStatue.value = NetworkStatus.NotFound
                    val a = result
                }
            }
        }.launchIn(viewModelScope)
    }

}