package net.whiteapps.ectmobileapp.serviceUser.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.person_24px
import kotlinx.datetime.LocalDate
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MyInputTextField2
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.serviceUser.domain.models.ServiceUser
import net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels.SelectServiceUserViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectServiceUser(
    setSelectedSU: (ServiceUser?) -> Unit,
    onNavigateReferral : () -> Unit,
) {
    val vm = koinViewModel<SelectServiceUserViewModel>()
    val suNetworkStatus = vm.suNetworkStatue.collectAsState()
    val serviceUser = vm.serviceUser.collectAsState()
    val showNewSUDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showSelectSUDialog = remember { mutableStateOf(false) }
    var selectedSU by rememberSaveable { mutableStateOf<ServiceUser??>(null) }
    val newNHSnoStr = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
//        Text("Service User")
        when (suNetworkStatus.value) {
            NetworkStatus.Failure -> {
                Text("Warning! an error occurred", color = Color.Red)
                showLoadingDialog.value = false
                showNewSUDialog.value = false
                showSelectSUDialog.value = false
            }

            NetworkStatus.Idle -> {
//                Text("No data requested from network")
                if (selectedSU == null) {
//                    Text(
//                        "Click to select service user",
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
                    showSelectSUDialog.value = true
                } else {
                    val nameStr =
                        selectedSU!!.firstname + " " + selectedSU!!.midname + " " + selectedSU!!.lastname
                    Text(nameStr, modifier = Modifier.padding(start = 16.dp))
                }
            }

            NetworkStatus.Loading -> {
//            showLoadingDialog.value = true
                Text("Checking for match in database")
            }

            NetworkStatus.Success -> {
                selectedSU = serviceUser.value
                setSelectedSU(serviceUser.value)
                showLoadingDialog.value = false
                showNewSUDialog.value = false
            }

            NetworkStatus.NotFound -> {
                Text("Add Service users details", color = Color.Red)
                showLoadingDialog.value = false
                showNewSUDialog.value = true
                showSelectSUDialog.value = false
            }
        }

        if (showSelectSUDialog.value) {
            Dialog(
                onDismissRequest = { showNewSUDialog.value = false }
            ) {
                Column(
                    modifier = Modifier
//                    .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(Color.White),
                ) {
                    Text("Input 10 digit NHS number")
                    MyInputTextField2(
                        modifier = Modifier,
                        labelValue = "NHS no", // stringResource(Res.string.),
                        imageRes = Res.drawable.person_24px,
                        iconDescription = "Person",
                        onTextChanged = {
                            val nhsNoStr = it
                            if (nhsNoStr.length == 10 && nhsNoStr.all { it -> it.isDigit() }) {
                                showLoadingDialog.value = true
                                newNHSnoStr.value = nhsNoStr
                                vm.getServiceUserByNhs(nhsNoStr)
                            }
                        },
                        initialText = "",
                        keyboardType = KeyboardType.Number
                    )
                    Button(onClick = {
                        showNewSUDialog.value = false
                        showLoadingDialog.value = false
                        showSelectSUDialog.value = false
                        onNavigateReferral()
                    }) {
                        Text("Close")
                    }


                    if (showLoadingDialog.value) {
                        Dialog(onDismissRequest = { showLoadingDialog.value = false }) {
                            Column(
                                modifier = Modifier
//                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomStart = 10.dp,
                                            bottomEnd = 10.dp
                                        )
                                    )
                                    .background(Color.White),
                            ) {
                                Text("Loading....")
//                                Button(onClick = {
//                                    val newSU = ServiceUser(
//                                        firstname = "Jason",
//                                        midname = "Dot",
//                                        lastname = "Data",
//                                        dob = LocalDate.parse("1961-12-12"),
//                                        id = 0,
//                                        nhsNo = "9876543210",
//                                        gender = "MALE"
//                                    )
//                                    selectedSU = newSU
//                                    setSelectedSU(newSU)
//                                    showLoadingDialog.value = false
//                                    showNewSUDialog.value = false
//                                    showSelectSUDialog.value = false
//                                }) {
//                                    Text("Select")
//                                }
//                                Button(onClick = {
//                                    showNewSUDialog.value = false
//                                    showLoadingDialog.value = false
//                                    showSelectSUDialog.value = false
//                                }) {
//                                    Text("Clear")
//                                }
                            }
                        }
                    }
                }
            }


        }

        if (showNewSUDialog.value) {

            Dialog(
                onDismissRequest = { showNewSUDialog.value = false }
            ) {

                if (showNewSUDialog.value) {
                    NewSUUI(
                        newNHSnoStr.value,
                        onCancel = {
                            vm.resetNetworkStatus()
                            showNewSUDialog.value = false
                        },
                        onSuccess = {
                            // continue to add referral
                            val su = it
                            setSelectedSU(it)
                            vm.resetNetworkStatus()
                            showLoadingDialog.value = false
                            showNewSUDialog.value = false
                            showNewSUDialog.value = false
                        },
                        onFailed = {
                            // TODO show error message
                            vm.resetNetworkStatus()
                            showNewSUDialog.value = false
                        }
                    )
                }
            }
        }


    }
}
