package net.whiteapps.ectmobileapp.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
   val refreshToken: String
)
