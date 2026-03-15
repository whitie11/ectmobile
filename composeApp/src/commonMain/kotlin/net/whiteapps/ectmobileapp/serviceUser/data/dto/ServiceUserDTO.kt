package net.whiteapps.ectmobileapp.serviceUser.data.dto



import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser

@Serializable
data class ServiceUserDTO(
   val serviceUser: ServiceUser
)