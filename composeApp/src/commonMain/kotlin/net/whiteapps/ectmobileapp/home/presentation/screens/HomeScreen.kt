package net.whiteapps.ectmobileapp.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.side_navigation_24px
import homeFeature.presentation.HomeViewModel
import kotlinx.coroutines.launch
import net.whiteapps.ectmobileapp.common.domain.models.IsLoggedIn
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.Blue
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.GradientBox
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MainMenu2
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.MainScaffold
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.SideMenu
import net.whiteapps.ectmobileapp.common.presentation.uiComponents.Tail600
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onNavigateNewReferral: () -> Unit
) {

    BackHandler(enabled = true) {
//        showConfirmDialog = true  // prevent app closing
    }

    val vm = koinViewModel<HomeViewModel>()
    val isLoggedIn = vm.isLoggedInState.value
    MainScaffold(
        screenContent = {
            MainMenu2(
                isLoggedIn,
                onNavigateReferral,
                onNavigateTreatment,
                onLogout
            )
        },
        onLogout = onLogout,
        onNavigateReferral = onNavigateReferral,
        onNavigateTreatment = onNavigateTreatment,
        screenTitle = vm.screenTitle,
        onNavigateHome = {},
        onNavigateBack = {},
        onNavigateNewReferral = onNavigateNewReferral
    )
}




