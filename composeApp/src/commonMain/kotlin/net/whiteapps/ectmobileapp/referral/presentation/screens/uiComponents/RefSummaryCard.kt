package net.whiteapps.ectmobileapp.referral.presentation.screens.uiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.common.domain.enums.Stage
import net.whiteapps.ectmobileapp.common.utils.DateFormatter
import net.whiteapps.ectmobileapp.referral.domain.models.ReferralRef

@Composable
fun RefSummaryCard(
    ref: ReferralRef,
    onClick: () -> Unit
) {
    val showReason = rememberSaveable { (mutableStateOf(false)) }
    val showReasonLabel = "Show Reason"
    var color = Color.LightGray
    if (ref.stage == Stage.PENDING) {
        color = Color(0xFFffb3b3)
    } else if (ref.stage == Stage.WAITING) {
        color = Color(0xFFb3ffbb)
    }
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
                ("CardView Clicked....") },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        )
    ) {
        Column {
            Row {
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = ref.firstName + " ")
                    if (ref.midName.isNotEmpty()) {
                        Text(text = ref.midName + " ")
                    }
                    Text(text = ref.lastName)
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "NHSno:")
                    Text(text = ref.nhsNo)
                }
            }
            Row {
                Row(modifier = Modifier.padding(8.dp)){
                    Text("Referred:")
                    val dateStr = ref.dateReferred.toString()
                    Text(DateFormatter.getFormattedDate(dateStr))
                }
                Row(modifier = Modifier.padding(8.dp)){
                    Text("Stage:")
                    Text(ref.stage.toString())
                }
            }

            Column(modifier = Modifier.clickable(onClick = { showReason.value = !showReason.value })){

                if (showReason.value) {
                    Text("Reason:")
                    Text(ref.reason,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                        .verticalScroll(rememberScrollState())
                    )
                } else {
                    Text(showReasonLabel)
                }
            }

        }
    }
}