package net.whiteapps.ectmobileapp.referral.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.common.domain.enums.Stage
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.Blue
import net.whiteapps.ectmobileapp.common.stateObjs.NetworkStatus
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.ReferralViewModel
import net.whiteapps.ectmobileapp.referral.presentation.screens.uiComponents.RefSummaryCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TabScreen(
    onShowDetails: (refId: Int) -> Unit,
    onGetNewReferral: () -> Unit
) {
    val vm = koinViewModel<ReferralViewModel>()

    val refList = vm.referralRefList.collectAsState()

    // State for tab selection
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    // Size parameters
//    val containerWidth by parameter(360f)
    val tabRowHeight = 56f

    // Edge padding parameter
    val edgePadding = 18f

    // Tab configuration
//    val tabCount by parameter(listOf("Five", "Seven", "Ten"), 0)
    val showIcons = false // by parameter(true)
    //   val iconPosition by parameter(listOf("Leading", "Top"), 0)

    // Color parameters
//    val useCustomColors by parameter(false)
//    val containerColor by parameter(TabRowDefaults.primaryContainerColor)
//    val contentColor by parameter(TabRowDefaults.primaryContentColor)

    // Indicator parameters
//    val showIndicator by parameter(true)
//    val useCustomIndicator by parameter(false)
//    val indicatorColor by parameter(MaterialTheme.colorScheme.primary)
//    val indicatorHeight by parameter(3f)

    // Divider parameters
//    val showDivider  by parameter(true)
    val dividerColor = MaterialTheme.colorScheme.outlineVariant
    val dividerThickness = 6f

    val refNetworkStatus = vm.refNetworkStatue.collectAsState()
    val errorMsg = vm.errorMsg.collectAsState()

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
            val tabs = listOf("All Current", "Pending", "Waiting", "Avondale", "Blackburn")
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            {
                // The primary scrollable tab row
                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    scrollState = scrollState,
                    modifier = Modifier
                        .height(tabRowHeight.dp)
                        .fillMaxWidth(),
                    containerColor = Blue, //TabRowDefaults.primaryContainerColor,
                    contentColor = Color.White, //TabRowDefaults.primaryContentColor,
                    edgePadding = edgePadding.dp,
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
                            width = Dp.Unspecified,
                            height = 6.dp
                        )
                    },
                    divider = {
                        HorizontalDivider(
                            thickness = dividerThickness.dp,
                            color = dividerColor
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->

                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) },
                            icon = null
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> {
                        if (refList.value.isNotEmpty()) {
                            refList.value.forEach { ref ->
                                RefSummaryCard(
                                    ref, onClick = { onShowDetails(ref.referralId) }
                                )
                            }
                        } else {
                            Text(text = "no data")
                        }
                        Button(onClick = {onGetNewReferral()}) {
                            Text("Add New Referral")
                        }
                    }

                    1 -> {
                        val pendingRefList = refList.value.filter { it.stage == Stage.PENDING }
                        if (pendingRefList.isNotEmpty()) {
                            pendingRefList.forEach { ref ->
                                RefSummaryCard(
                                    ref,
                                    onClick = { onShowDetails(ref.referralId) }
                                )
                            }
                        } else {
                            Text(text = "no data")
                        }
                    }

                    2 -> {
                        val waitingRefList = refList.value.filter { it.stage == Stage.WAITING }
                        if (waitingRefList.isNotEmpty()) {
                            waitingRefList.forEach { ref ->
                                RefSummaryCard(ref, onClick = { onShowDetails(ref.referralId) })
                            }
                        } else {
                            Text(text = "no data")
                        }
                    }

                    else -> {


                        // Content area to see the effect of tab selection
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(top = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = tabs[selectedTabIndex].second,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
                                Text(
                                    text = "TODO show ${tabs[selectedTabIndex]} Content",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        NetworkStatus.NotFound -> Text(
            "No data found from network",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}