package net.whiteapps.ectmobileapp.serviceUser.presentation.events

import net.whiteapps.enums.Gender

sealed class NewSUFormEvent {
    data class FirstnameChanged(val firstname: String) : NewSUFormEvent()
    data class MidnameChanged(val midname: String) : NewSUFormEvent()
    data class LastnameChanged(val lastname: String) : NewSUFormEvent()
    data class DobChanged(val dob: String) : NewSUFormEvent()
    data class NHSnoChanged(val nhsNo: String) : NewSUFormEvent()
    data class GenderChanged(val gender: Gender) : NewSUFormEvent()
    data object Submit : NewSUFormEvent()
}