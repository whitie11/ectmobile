package auth.presentation.stateObjs

sealed class LogInState {
    data object SignedOut : LogInState()
    data object InProgress : LogInState()
    data object Error : LogInState()
    data object SignedIn : LogInState()
}