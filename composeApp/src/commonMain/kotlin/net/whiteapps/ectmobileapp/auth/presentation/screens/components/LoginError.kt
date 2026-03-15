package auth.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import auth.presentation.events.LoginUIEvent
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.ect_treatment_log
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.GradientBox
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginError(
    onLoginEvent: (LoginUIEvent) -> Unit,
    error: String,
) {
    LoginErrorScreen(error, onLoginEvent)
}


@Composable
fun LoginErrorScreen(
    error: String,
    onLoginEvent: (LoginUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
//    val isImeVisible by rememberImeState()
    GradientBox(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
//            val animatedUpperSectionRatio by animateFloatAsState(
//                targetValue = if (isImeVisible) 0.15f else 0.5f,
//                label = "" )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.ect_treatment_log),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 40.dp,
                                bottomStart = 40.dp,
                                bottomEnd = 40.dp,

                                )
                        )
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(
                        text = error,
                        modifier = modifier,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Button(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .padding(horizontal = 16.dp),

                        onClick = { onLoginEvent(LoginUIEvent.DoLogout) }
                    ) {
                        Text(text = "Retry")
                    }

                }
            }
        }
    }
}


