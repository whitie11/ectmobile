package net.whiteapps.ectmobileapp.util

//import com.auth0.android.jwt.JWT
//import auth.domain.models.JwtPayload
//
//fun decodeJWT(token: String): JwtPayload {
//    val jwt = JWT(token)
//    val jwtPayload = JwtPayload(
//        exp = jwt.expiresAt,
//        iat = jwt.issuedAt,
//        userID = jwt.getClaim("user_id").asInt()
//    )
//    return jwtPayload
//}

import io.ktor.util.decodeBase64Bytes
import kotlinx.serialization.json.Json
import net.whiteapps.ectmobileapp.common.domain.models.JwtPayload


fun decodeJWT(token: String): JwtPayload {
    val json = Json { ignoreUnknownKeys = true }
    val parts = token.split(".")
    if (parts.size != 3) throw IllegalArgumentException("Invalid JWT token")

    val payloadJson = parts[1].decodeBase64String()
   val res2 = json.decodeFromString<JwtPayload>(payloadJson)
    return res2
}

fun String.decodeBase64String(): String {
    val decodedBytes = decodeBase64Bytes()
    val res =  decodedBytes.decodeToString()
    return res
}