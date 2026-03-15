package net.whiteapps.ectmobileapp.auth.presentation.screens
//
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.GradientBox
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.CustomCircularProgressBar
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MyInputTextField
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.PasswordTextField
import auth.presentation.events.LoginUIEvent
import auth.presentation.stateObjs.TokenRequestUIState
import auth.presentation.stateObjs.TokenState
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.ect_pathway
import ectmobileapp.composeapp.generated.resources.person_24px
import ectmobileapp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.stringResource

//@Composable
//expect fun keyboardVisibilityState(): State<Boolean>

@Composable
fun LoginUI(
    tokenState: State<TokenState>,
    tokenRequestUIState: TokenRequestUIState,
    onLoginEvent: (LoginUIEvent) -> Unit,
) {
//    val isKeyboardVisible by keyboardVisibilityState()
    GradientBox(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight(),
//            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            val animatedUpperSectionRatio by animateFloatAsState(
                targetValue = 0.15f,     //   if (isKeyboardVisible) 0.15f else 0.5f,
                label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f),
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    text = stringResource(Res.string.ect_pathway),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
//                    .fillMaxHeight()
                    .width(400.dp)
                    .padding(8.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 40.dp,
                            bottomStart = 40.dp,
                            bottomEnd = 40.dp
                        )
                    )
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                if (tokenState.value.isLoading) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        text = "Loading..., please wait!",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        text = "Please login to continue",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                MyInputTextField(
                    modifier = Modifier,
                    labelValue = stringResource(Res.string.username),
                    imageRes= Res.drawable.person_24px,
                    iconDescription = "Person",
                    onTextChanged = {
                        onLoginEvent(LoginUIEvent.UsernameChanged(it))
                    },
                    initialText = tokenRequestUIState.username
                )
                PasswordTextField(
                    modifier = Modifier,
                    onTextChanged = {
                        onLoginEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    initialText = tokenRequestUIState.password
                )
                if (tokenState.value.isLoading) {
                    CustomCircularProgressBar()
                } else {
                    Button(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .padding(horizontal = 16.dp)
                            .align(Alignment.End),
//                        enabled = (
//                                tokenRequestUIState.username.isNotEmpty()
//                                        && tokenRequestUIState.password.isNotEmpty()
//                                ),

                        onClick = { onLoginEvent(LoginUIEvent.DoLogin) }

                    ) {
                        Text(text = "Submit")
                    }
                }
            }

        }

    }
}
