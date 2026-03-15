package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun SideMenu(
    onNavigateReferral: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateNewReferral: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onNavigateHome) {
            Text("Main Menu")
        }
        Button(
            onClick = onNavigateReferral) {
            Text("Current Referrals")
        }
        Button(
            onClick = onNavigateNewReferral) {
            Text("Add Referral")
        }
    }
}