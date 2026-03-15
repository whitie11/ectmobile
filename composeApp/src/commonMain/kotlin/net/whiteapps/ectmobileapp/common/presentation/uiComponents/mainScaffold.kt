package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.side_navigation_24px
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    screenContent: @Composable () -> Unit,
    screenTitle: String,
    onLogout: () -> Unit,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onNavigateHome: () -> Unit,
    showBackButton: Boolean = false,
    onNavigateBack: () -> Unit,
    onNavigateNewReferral: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SideMenu(onNavigateReferral,
                    onNavigateHome,
                    onNavigateNewReferral)
            }
        },
    )
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Blue,
                        titleContentColor = Color.White,
                    ),
                    title = {
                        Text(screenTitle)
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                        ) {
                            Icon(
                                painterResource(Res.drawable.side_navigation_24px),
//                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Main Menu",
                            )
                        }
                    },
                    actions = {
                        Button(onClick = onLogout) {
                            Text("Logout")
                        }
                    },
                )
            },

            bottomBar = {
                BottomAppBar(
                    containerColor = Tail600,
                    contentColor = Color.White,
                ) {
                    Text(
                        modifier = Modifier
//                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                    )
                    Spacer(Modifier.weight(1f))

                    if (showBackButton) {
                        Button(onClick = onNavigateBack) {
                            Text("<<Back")
                        }
                    }
                }
            },

//            floatingActionButton = {
//                FloatingActionButton(onClick = { }) {
//                    Icon(Icons.Default.Add, contentDescription = "Add")
//                }
//            }

        ) { innerPadding ->
            GradientBox(modifier = Modifier
                .padding(innerPadding)
            .fillMaxSize()) {
                screenContent()
            }
        }
    }
}