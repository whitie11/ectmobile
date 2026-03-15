package net.whiteapps.ectmobileapp.auth.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import auth.presentation.screens.components.LoginError
import auth.presentation.stateObjs.LogInState
import net.whiteapps.ectmobileapp.auth.presentation.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(

    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit
) {
    val vm = koinViewModel<AuthViewModel>()
    val tokenRequestUIState = vm.tokenRequestUIState.value
    val onLoginEvent = vm::onLoginEvent
    val tokenState = vm.tokenState.collectAsState()
val loginState = vm.loginState.collectAsState()
    when (loginState.value) {
        is LogInState.SignedOut ->
            LoginUI(tokenState, tokenRequestUIState, onLoginEvent) // Display login UI components
        is LogInState.InProgress -> {
            // Display loading spinner
//            CustomCircularProgressBar()
            Text("Connecting to Database")
        }
        is LogInState.Error -> {
//            val context = LocalContext.current
//            Toast.makeText(context, tokenState.error, Toast.LENGTH_SHORT).show()
            LoginError(onLoginEvent,tokenState.value.error )
        }    // Display error toast
        is LogInState.SignedIn -> {
            // Using the SignIn state as a trigger to navigate
             LaunchedEffect(Unit){
                onLogin()
            }
        }
    }
}