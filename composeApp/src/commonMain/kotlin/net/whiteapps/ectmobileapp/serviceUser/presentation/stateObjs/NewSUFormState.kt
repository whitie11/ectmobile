package net.whiteapps.ectmobileapp.serviceUser.presentation.stateObjs

import net.whiteapps.enums.Gender

data class NewSUFormState(
    val firstname: String = "",
    val firstnameError: String? = null,
    val lastname: String = "",
    val lastnameError: String? = null,
    val midname: String = "",
    val midnameError: String? = null,
    val dob: String = "",
    val dobError: String? = null,
    val nhsNo: String = "",
    val nhsNoError: String? = null,
    val gender: Gender? = null,
    val genderError: String? = null
    )