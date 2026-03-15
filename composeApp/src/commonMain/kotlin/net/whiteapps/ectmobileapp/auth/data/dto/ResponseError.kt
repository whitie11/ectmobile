package net.whiteapps.ectmobileapp.auth.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    @Contextual
    val code: String,
    val message: String
)