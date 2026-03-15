package net.whiteapps.ectmobileapp.serviceUser.data.dto



import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.whiteapps.enums.Gender

@Serializable
data class NewServiceUserDTO(
   val firstname: String,
   val lastname: String,
   val midname: String,
   @Contextual
   val dob: LocalDate,
   val nhsNo: String,
   val gender: Gender?,
)