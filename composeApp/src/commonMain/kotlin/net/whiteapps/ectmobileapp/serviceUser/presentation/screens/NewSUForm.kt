package net.whiteapps.ectmobileapp.serviceUser.presentation.screens

//import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.first_name
import ectmobileapp.composeapp.generated.resources.last_name
import ectmobileapp.composeapp.generated.resources.mid_name
import ectmobileapp.composeapp.generated.resources.person_24px
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MyInputTextField2
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.presentation.screens.uiComponents.GenderDropdown
import net.whiteapps.ectmobileapp.serviceUser.presentation.events.NewSUFormEvent
import net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels.NewSUViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


//@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Composable
//fun NewSUForm(
//    onLogout: () -> Unit,
//    onNavigateReferral: () -> Unit,
//    onNavigateTreatment: () -> Unit,
//    onNavigateHome: () -> Unit,
//) {
//
//    MainScaffold(
//        screenContent = { NewSUUI() },
//        screenTitle = "New Service User",
//        onLogout = onLogout,
//        onNavigateReferral = onNavigateReferral,
//        onNavigateTreatment = onNavigateTreatment,
//        onNavigateHome = onNavigateHome,
//        onNavigateBack = onNavigateReferral,
//        showBackButton = true,
//        onNavigateNewReferral = {
//        }
//    )
//
//}

//@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun NewSUUI(
    nhsNo: String,
    onCancel: () -> Unit,
    onSuccess: (newSU: ServiceUser) -> Unit,
    onFailed: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel = koinViewModel<NewSUViewModel>()
    val state = viewModel.state
    val newSuNetworkStatus = viewModel.newSuNetworkStatue.collectAsState()
    val canSubmit = viewModel.canSubmit.collectAsState()


    when (newSuNetworkStatus.value) {
        NetworkStatus.Failure -> {
            onFailed()
        }

        NetworkStatus.Loading -> {} //TODO()
        NetworkStatus.NotFound -> {}
        NetworkStatus.Success -> {
            onSuccess(viewModel.serviceUser.value!!)
        }

        NetworkStatus.Idle -> {
            viewModel.onEvent(NewSUFormEvent.NHSnoChanged(nhsNo))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
//                    .fillMaxHeight()
                        .fillMaxWidth(0.98f)
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(Color.White)
//                .verticalScroll(rememberScrollState())
                        .clickable {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
//                verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text("New Service User Screen")
                    Text("NHS no: $nhsNo")


                    MyInputTextField2(
                        modifier = Modifier.focusRequester(focusRequester),
                        labelValue = stringResource(Res.string.first_name),
                        imageRes = Res.drawable.person_24px,
                        iconDescription = "Person",
                        onTextChanged = {
                            viewModel.onEvent(NewSUFormEvent.FirstnameChanged(it))
                        },
                        initialText = ""
                    )
                    if (state.firstnameError != null) {
                        Text(state.firstnameError, color = Color.Red)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    MyInputTextField2(
                        modifier = Modifier.focusRequester(focusRequester),
                        labelValue = stringResource(Res.string.mid_name),
                        imageRes = Res.drawable.person_24px,
                        iconDescription = "Person",
                        onTextChanged = {
                            viewModel.onEvent(NewSUFormEvent.MidnameChanged(it))
                        },
                        initialText = ""
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    MyInputTextField2(
                        modifier = Modifier.focusRequester(focusRequester),
                        labelValue = stringResource(Res.string.last_name),
                        imageRes = Res.drawable.person_24px,
                        iconDescription = "Person",
                        onTextChanged = {
                            viewModel.onEvent(NewSUFormEvent.LastnameChanged(it))
                        },
                        initialText = ""
                    )
                    if (state.lastnameError != null) {
                        Text(state.lastnameError, color = Color.Red)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    MyInputTextField2(
                        modifier = Modifier.focusRequester(focusRequester),
                        labelValue = "DoB", //stringResource(Res.string.date_of_birth),
                        imageRes = Res.drawable.person_24px,
                        iconDescription = "Person",
                        onTextChanged = {
                            viewModel.onEvent(NewSUFormEvent.DobChanged(it))
                        },
                        initialText = "",
                        keyboardType = KeyboardType.Decimal
                    )
                    if (state.dobError != null) {
                        Text(state.dobError, color = Color.Red)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    GenderDropdown(modifier = Modifier)
                    if (state.genderError != null) {
                        Text(state.genderError, color = Color.Red)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text("Can Submit: ${canSubmit.value}")

                    Button(
                        enabled = canSubmit.value,
                        onClick = {
                        viewModel.onEvent(NewSUFormEvent.Submit)
                        focusManager.clearFocus()
                    }) {
                        Text("Submit")
                    }

                    Button(onClick = {
                        onCancel()
                    }) {
                        Text("Cancel")
                    }

                }

            }
        }
    }

}

