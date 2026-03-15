package net.whiteapps.ectmobileapp.serviceUser.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable()
data class ServiceUser(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val midname: String,
    @Contextual
    val dob: LocalDate,
    val nhsNo: String,
    val gender: String,
)