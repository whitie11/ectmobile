package net.whiteapps.ectmobileapp.common.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class JwtPayload(
    @SerialName("aud") val aud: String? = null,
    @SerialName("iss") val issuer: String? = null,
    @SerialName("exp") val expiresAt: Long? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("role") val role: String? = null,
    @SerialName("user_id") val userID: String? = null
)