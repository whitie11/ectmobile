package net.whiteapps.ectmobileapp.referral.presentation.screens

//import common.presentation.uiComponents.Blue
//import common.presentation.uiComponents.GradientBox
//import common.presentation.uiComponents.Tail600
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.FullScreenTextbox
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MainScaffold
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.common.utils.DateFormatter
import net.whiteapps.ectmobileapp.progressNotes.presentation.screens.TestScreen
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.ReferralDetailsViewModel
import net.whiteapps.ectmobileapp.referral.presentation.screens.uiComponents.ChangeStageDialog
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ReferralDetailScreen(
    refId: Int,
    onLogout: () -> Unit,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onNavigateHome: () -> Unit
) {

    MainScaffold(
        screenContent = { DetailScreen(refId) },
        screenTitle = "Referral Details",
        onLogout = onLogout,
        onNavigateReferral = onNavigateReferral,
        onNavigateTreatment = onNavigateTreatment,
        onNavigateHome = onNavigateHome,
        onNavigateBack = onNavigateReferral,
        showBackButton = true,
        onNavigateNewReferral = {}
    )

}

@Composable
fun DetailScreen(refId: Int) {

    val vm = koinViewModel<ReferralDetailsViewModel>()
    val refNetworkStatus = vm.refNetworkStatue.collectAsState()
    val errorMsg = vm.errorMsg.collectAsState()

    LaunchedEffect(refId) {
        vm.getReferral(refId)
    }

    val ref = vm.selectedRef.collectAsState()


    when (refNetworkStatus.value) {
        NetworkStatus.Failure -> Text(
            "Warning!  ${errorMsg.value}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        NetworkStatus.Idle -> Text(
            "No data requested from network",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        NetworkStatus.Loading -> Text(
            "Loading data, please wait....",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        NetworkStatus.Success -> {

            var showChangeStageDialog by remember { mutableStateOf(false) }

            if (showChangeStageDialog) {
                ChangeStageDialog(
                    onDismissRequest = { showChangeStageDialog = false },
                )
            }


            var showReasonFullScreen by remember { mutableStateOf(false) }
            if (showReasonFullScreen) {
                FullScreenTextbox(
                    onDismissRequest = { showReasonFullScreen = false },
                    text = ref.value?.reason ?: "No reason provided",
                    onCancel = { showReasonFullScreen = false },
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.98f)
//                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(Color.White),
//                verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(
                        "Service User: ${ref.value?.serviceUser?.firstname} ${ref.value?.serviceUser?.midname} ${ref.value?.serviceUser?.lastname}",
//                    style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp
                    )
                    Row {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Text("Referred:")
                            val dateStr = ref.value?.dateReferred.toString()
                            Text(DateFormatter.getFormattedDate(dateStr))
                        }
                        Spacer(Modifier.weight(1f))

                        Button(onClick = { showChangeStageDialog = true }) {
                            Text("Stage: ")
                            Text(ref.value?.stage.toString())
                        }
                    }

                    Card(
                        border = BorderStroke(2.dp, Color.Red),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .height(200.dp)
//                     .size(width = 240.dp, height = 100.dp)
                            .fillMaxWidth(0.95f)
                            .align(alignment = Alignment.CenterHorizontally),
                    ) {

                        Column(
                            modifier = Modifier
//                                .height(500.dp)
                                .padding(8.dp)
                                .verticalScroll(rememberScrollState())
                                .weight(weight = 1f, fill = false)
                                .clickable(true, onClick = {
                                    showReasonFullScreen = true
                                })
                        ) {
                            ref.value?.reason?.let { Text(it) }
                        }
                    }
                    Text("Reason for referral",
                        modifier = Modifier
                            .offset(x= 30.dp , y= (-215).dp)
                            .background(Color.White))
                    TestScreen(ref.value?.id ?: 0)
                }
            }
        }

        NetworkStatus.NotFound -> {
            Text("No data found")
        }
    }
}