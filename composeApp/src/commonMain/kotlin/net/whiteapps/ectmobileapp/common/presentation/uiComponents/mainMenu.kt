package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.common.domain.models.IsLoggedIn

@Composable
fun MainMenu2(
    isLoggedIn: IsLoggedIn,
    onNavigateReferral: () -> Unit,
    onNavigateTreatment: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
//        Text("Home Screen")
//        Text("userID = " + isLoggedIn.userID.toString())
//        Text("username = " + isLoggedIn.username)
//        Text("user Role = " + isLoggedIn.role)

        Spacer(modifier = Modifier.Companion.size(4.dp))
        Button(onClick = onNavigateReferral) {
            Text("Current Referrals")
        }
        Spacer(modifier = Modifier.Companion.size(4.dp))
        Button(onClick = {}) {
            Text("Clinic List")
        }
//                    Spacer(modifier = Modifier.Companion.size(4.dp))
//                    Button(onClick = {}) {
//                        Text("Assessments")
//                    }
        Spacer(modifier = Modifier.Companion.size(4.dp))
        Button(onClick = onNavigateTreatment) {
            Text("Treatment")
        }
        Spacer(modifier = Modifier.Companion.size(10.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}