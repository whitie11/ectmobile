package net.whiteapps.ectmobileapp.referral.presentation.viewModels

// import net.whiteapps.ectmobileapp.referral.domain.use_case.validation.ValidateDob
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.whiteapps.ectmobileapp.referral.data.dto.NewReferralDTO
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralServicerUserResponseDTO
import net.whiteapps.ectmobileapp.referral.domain.models.Referral
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.referral.domain.use_case.GetOpenReferralUseCase
import net.whiteapps.ectmobileapp.referral.domain.use_case.SaveReferralUseCase
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateDob
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateFirstname
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateGender
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateLastname
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateNHSno
import net.whiteapps.ectmobileapp.referral.domain.validation.ValidateReason
import net.whiteapps.ectmobileapp.referral.domain.validation.ValidateReferrer
import net.whiteapps.ectmobileapp.referral.domain.validation.ValidateReferrersEmail
import net.whiteapps.ectmobileapp.referral.domain.validation.ValidateServiceUser
import net.whiteapps.ectmobileapp.referral.presentation.events.NewReferralFormEvent
import net.whiteapps.ectmobileapp.referral.presentation.stateObjs.NewReferralFormState
import net.whiteapps.ectmobileapp.serviceUser.domain.validation.ValidateMidname
import net.whiteapps.ectmobileapp.util.Resource

class NewReferralViewModel(
    private val refRepository: ReferralRepository
) : ViewModel() {
    private val validateServiceUser: ValidateServiceUser = ValidateServiceUser(refRepository)
    private val validateReason: ValidateReason = ValidateReason()
    private val validateReferrer: ValidateReferrer = ValidateReferrer()
    private val validateReferrersEmail: ValidateReferrersEmail = ValidateReferrersEmail()

    var state by mutableStateOf(NewReferralFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _canSubmit = MutableStateFlow<Boolean>(false)
    val canSubmit = _canSubmit.asStateFlow()

    val getOpenReferralUseCase: GetOpenReferralUseCase = GetOpenReferralUseCase(
        refRepository = refRepository,
    )
    val saveReferralUseCase: SaveReferralUseCase = SaveReferralUseCase(
        refRepository = refRepository,
    )
//    @OptIn(ExperimentalTime::class)
    fun onEvent(event: NewReferralFormEvent) {
        when (event) {
            is NewReferralFormEvent.ServiceUserChanged -> {
                state = state.copy(serviceUser = event.serviceUser)
                val serviceUserResult = validateServiceUser.execute(state.serviceUser)
                if (serviceUserResult.successful == true) {
                    getOpenReferral(state.serviceUser!!.id)
                }

            }

            is NewReferralFormEvent.ReasonChanged -> {
                state = state.copy(reason = event.reason)
            }

            is NewReferralFormEvent.ReferrerChanged -> {
                state = state.copy(referrer = event.referrer)
            }

            is NewReferralFormEvent.ReferrersEmailChanged -> {
                state = state.copy(referrersEmail = event.referrersEmail)
            }

            NewReferralFormEvent.Submit -> {
                submitData()
            }

        }
    setCanSubmit()
}
    fun getOpenReferral(suId: Int) {
        getOpenReferralUseCase(suId).onEach { result: Resource<ReferralServicerUserResponseDTO> ->
            when (result) {
                is Resource.Success -> {
                    // Open referral found for service user: Trigger "ask to close"
                    validationEventChannel.send(ValidationEvent.PatientExists)
                    val x = 1
                }
                is Resource.Error -> {
                    // No open referral found for service user
                    val x = 1
                }

                is Resource.Loading -> {
//                    _refNetworkStatue.value = NetworkStatus.Loading
//                    _errorMsg.value = ""
                    val x = 1
                }

                is Resource.Nothing<*> -> {}

            }
        }.launchIn(viewModelScope)
    }

    fun  resetServiceUserError() {
        state = state.copy(
            serviceUserError = null)
    }
    sealed class ValidationEvent {
        object idle: ValidationEvent()
        object Success : ValidationEvent()
        object PatientExists: ValidationEvent()

    }

    private fun setCanSubmit() {
        _canSubmit.value =  state.serviceUser != null &&
                state.reason.isNotBlank() &&
                state.referrer.isNotBlank() &&
                state.referrersEmail.isNotBlank()
    }

    private fun submitData() {
        val serviceUserResult = validateServiceUser.execute(state.serviceUser)
        val reasonResult = validateReason.execute(state.reason)
        val referrerResult = validateReferrer.execute(state.referrer)
        val referrersEmailResult = validateReferrersEmail.execute(state.referrersEmail)

        val hasError = listOf(
            serviceUserResult,
            reasonResult,
            referrerResult,
            referrersEmailResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                serviceUserError = serviceUserResult.errorMessage,
                reasonError = reasonResult.errorMessage,
                referrerError = referrerResult.errorMessage,
                referrersEmailError = referrersEmailResult.errorMessage
            )
            return
        }

        val referral = NewReferralDTO(
            serviceUserId = state.serviceUser!!.id,
            referrer = state.referrer,
            referrersEmail = state.referrersEmail,
            reason = state.reason
        )

        saveReferralUseCase(referral).onEach { result: Resource<Referral> ->
            when (result) {
                is Resource.Success -> {
                    validationEventChannel.send(ValidationEvent.Success)

                    val x = 1
                }
                is Resource.Error -> {

                    val x = 1
                }

                is Resource.Loading -> {
//                    _refNetworkStatue.value = NetworkStatus.Loading
//                    _errorMsg.value = ""
                    val x = 1
                }

                is Resource.Nothing<*> -> {}

            }
        }.launchIn(viewModelScope)



//        viewModelScope.launch {
//            validationEventChannel.send(ValidationEvent.Success)
//        }

    }



}












