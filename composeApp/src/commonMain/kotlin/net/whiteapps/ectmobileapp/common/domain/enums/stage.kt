package net.whiteapps.ectmobileapp.common.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Stage {
    PENDING,
    NOT_ALLOCATED,
    DECLINED,
    CLOSED,
    WAITING,
    ACCEPTED_P,
    ACCEPTED_B,
    TREATMENT_P,
    TREATMENT_B,
    COMPLETED
}