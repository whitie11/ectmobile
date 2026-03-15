package net.whiteapps.ectmobileapp.referral.presentation.screens

//import common.presentation.uiComponents.Blue
//import common.presentation.uiComponents.GradientBox
//import common.presentation.uiComponents.Tail600
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MainScaffold
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.ReferralViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ReferralScreen(
    onLogout: () -> Unit,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateDetails: (refId: Int) -> Unit,
    onNavigateNewReferral: () -> Unit
) {
    val vm = koinViewModel<ReferralViewModel>()
    vm.getAllOpenReferralRef()

    MainScaffold(
        screenContent = {
            TabScreen(
                onShowDetails = onNavigateDetails,
                onGetNewReferral = onNavigateNewReferral
            )
        },
        screenTitle = vm.screenTitle,
        onLogout = onLogout,
        onNavigateReferral = onNavigateReferral,
        onNavigateTreatment = onNavigateTreatment,
        onNavigateHome = onNavigateHome,
        showBackButton = true,
        onNavigateBack = onNavigateHome,
        onNavigateNewReferral = onNavigateNewReferral
    )

}
