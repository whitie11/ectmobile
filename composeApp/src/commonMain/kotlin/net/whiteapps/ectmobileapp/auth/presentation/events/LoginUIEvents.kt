package auth.presentation.events

sealed class LoginUIEvent {
    data class UsernameChanged(val username: String): LoginUIEvent()
    data class PasswordChanged(val password: String): LoginUIEvent()
    data object DoLogin: LoginUIEvent()
    data object DoLogout: LoginUIEvent()
}