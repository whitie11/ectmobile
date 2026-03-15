package auth.presentation.stateObjs

data class TokenState(
    val isLoading: Boolean = false,
    val access: String = "",
    val refresh: String = "",
    val error: String = ""
)
