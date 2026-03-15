package net.whiteapps.ectmobileapp.referral.presentation.screens

//import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.first_name
import ectmobileapp.composeapp.generated.resources.last_name
import ectmobileapp.composeapp.generated.resources.mid_name
import ectmobileapp.composeapp.generated.resources.person_24px
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MainScaffold
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MyInputTextField2
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MyInputTextField3
import net.whiteapps.ectmobileapp.referral.presentation.events.NewReferralFormEvent
import net.whiteapps.ectmobileapp.serviceUser.presentation.screens.SelectServiceUser
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.NewReferralViewModel
import net.whiteapps.ectmobileapp.serviceUser.presentation.events.NewSUFormEvent
import net.whiteapps.ectmobileapp.serviceUser.presentation.screens.NewSUUI
import net.whiteapps.ectmobileapp.serviceUser.presentation.screens.uiComponents.GenderDropdown
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewReferralForm(
    onLogout: () -> Unit,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onNavigateHome: () -> Unit,
) {

    MainScaffold(
        screenContent = { NewReferralUI(onNavigateReferral) },
        screenTitle = "New Referral",
        onLogout = onLogout,
        onNavigateReferral = onNavigateReferral,
        onNavigateTreatment = onNavigateTreatment,
        onNavigateHome = onNavigateHome,
        onNavigateBack = onNavigateReferral,
        showBackButton = true,
        onNavigateNewReferral = {}
    )

}

//@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun NewReferralUI(
    onNavigateReferral : () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel = koinViewModel<NewReferralViewModel>()
    val state = viewModel.state

    val canSubmit = viewModel.canSubmit.collectAsState()

    val validationEvents by viewModel.validationEvents.collectAsState(initial = null)

    val closeRefDialog = remember { mutableStateOf(false) }

    LaunchedEffect(validationEvents) {
        when (validationEvents) {
            is NewReferralViewModel.ValidationEvent.Success -> {
                onNavigateReferral()
            }
            is NewReferralViewModel.ValidationEvent.PatientExists -> {
                closeRefDialog.value = true
            }
           is NewReferralViewModel.ValidationEvent.idle -> {}
            null -> {}
            }
    }



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
            .verticalScroll(rememberScrollState())
            .clickable {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
//                verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text("New Referral Screen")


        if (state.serviceUser == null) {
            SelectServiceUser(
                setSelectedSU = {
                    viewModel.onEvent(NewReferralFormEvent.ServiceUserChanged(it))
                },
                onNavigateReferral = onNavigateReferral
            )
            if (state.serviceUserError != null) {
                Text(state.serviceUserError, color = Color.Red)
            }
        } else {
            if (state.serviceUserError != null) {
                Text(state.serviceUserError, color = Color.Red)
                closeRefDialog.value = true

            } else {
            Text("Service User: ${state.serviceUser.firstname} ${state.serviceUser.midname} ${state.serviceUser.lastname}")

            MyInputTextField2(
                modifier = Modifier.focusRequester(focusRequester),
                labelValue = "Referrers name", //stringResource(Res.string.first_name),
                imageRes = Res.drawable.person_24px,
                iconDescription = "Person",
                onTextChanged = {
                    viewModel.onEvent(NewReferralFormEvent.ReferrerChanged(it))
                },
                initialText = "",
            )
            if (state.referrerError != null) {
                Text(state.referrerError, color = Color.Red)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            MyInputTextField2(
                modifier = Modifier.focusRequester(focusRequester),
                labelValue = "Referrers email", //stringResource(Res.string.first_name),
                imageRes = Res.drawable.person_24px,
                iconDescription = "Person",
                onTextChanged = {
                    viewModel.onEvent(NewReferralFormEvent.ReferrersEmailChanged(it))
                },
                initialText = "",
                keyboardType = KeyboardType.Email,
            )
            if (state.referrersEmailError != null) {
                Text(state.referrersEmailError, color = Color.Red)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            MyInputTextField3(
                modifier = Modifier.focusRequester(focusRequester),
                labelValue = "Reason for referral", //stringResource(Res.string.first_name),
                onTextChanged = {
                    viewModel.onEvent(NewReferralFormEvent.ReasonChanged(it))
                },
                initialText = "",
            )
            if (state.reasonError != null) {
                Text(state.reasonError, color = Color.Red)
            }
            Spacer(modifier = Modifier.padding(8.dp))

        }
        Text("Can Submit: ${canSubmit.value}")
    }
        Button(
            enabled = canSubmit.value,
            onClick = {
                viewModel.onEvent(NewReferralFormEvent.Submit)
                focusManager.clearFocus()
            }) {
            Text("Submit")
        }

    }

}

    if (closeRefDialog.value) {

        Dialog(
            onDismissRequest = {
                closeRefDialog.value = false }
        ) {
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

                    if (state.serviceUser != null) {
                        Text("Service User: ${state.serviceUser.firstname} ${state.serviceUser.midname} ${state.serviceUser.lastname} has an open referral")

                    }

//                    Button(onClick = {
//                        viewModel.resetServiceUserError()
//                        closeRefDialog.value = false }) {
//                        Text("Close Referral and continue")
//                    }
                    Button(onClick = {
                        onNavigateReferral()
                        closeRefDialog.value = false }) {
                        Text("Cancel")
                    }

                }

            }
        }
    }

}

