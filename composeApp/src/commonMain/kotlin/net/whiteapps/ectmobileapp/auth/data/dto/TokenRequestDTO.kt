package net.whiteapps.ectmobileapp.auth.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class TokenRequestDTO(
    val name: String,
    val password: String,

)
