package net.whiteapps.ectmobileapp.progressNotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.progressNotes.domain.models.ProgressNote
import net.whiteapps.ectmobileapp.progressNotes.domain.repository.ProgressNotesRepository
import net.whiteapps.ectmobileapp.progressNotes.domain.use_case.GetProgressNotesReferralUseCase
import net.whiteapps.ectmobileapp.referral.data.dto.ReferralRefResponse
import net.whiteapps.ectmobileapp.util.Resource
import androidx.lifecycle.viewModelScope
import net.whiteapps.ectmobileapp.progressNotes.data.dto.ProgressNotesResponse

class ProgressNotesViewModel(private val progressNotesRepository: ProgressNotesRepository)
: ViewModel() {
    private val _progressNotes = MutableStateFlow<List<ProgressNotesResponse>>(emptyList())
    val progressNotes = _progressNotes.asStateFlow()

    val getProgressNotesReferralUseCase: GetProgressNotesReferralUseCase = GetProgressNotesReferralUseCase(
       refId = 0,
        progressNotesRepository = progressNotesRepository
    )

    private val _errorMsg = MutableStateFlow("")
    val errorMsg = _errorMsg.asStateFlow()

    private val _notesNetworkStatue = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val notesNetworkStatue = _notesNetworkStatue.asStateFlow()


    fun getNotesReferral(refId: Int) {
        getProgressNotesReferralUseCase(refId).onEach { result: Resource<List<ProgressNotesResponse>> ->
            when (result) {
                is Resource.Success -> {
                    _notesNetworkStatue.value = NetworkStatus.Success
                    result.data?.let { _progressNotes.value = it }
                    _errorMsg.value = ""
                }

                is Resource.Error -> {
                    _notesNetworkStatue.value = NetworkStatus.Failure
                    _errorMsg.value = result.message ?: "An unexpected error occurred"
                }

                is Resource.Loading -> {
                    _notesNetworkStatue.value = NetworkStatus.Loading
                    _errorMsg.value = ""
                }

                is Resource.Nothing<*> -> {
                    _notesNetworkStatue.value = NetworkStatus.NotFound
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
//        getNotesReferral(1)
    }
}

