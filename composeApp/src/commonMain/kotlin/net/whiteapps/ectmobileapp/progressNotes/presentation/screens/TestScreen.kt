package net.whiteapps.ectmobileapp.progressNotes.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.common.utils.DateFormatter
import net.whiteapps.ectmobileapp.progressNotes.presentation.ProgressNotesViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TestScreen(refId: Int) {
    val vm = koinViewModel<ProgressNotesViewModel>()
    if (refId != 0) {
        LaunchedEffect(refId) {
            vm.getNotesReferral(refId)
        }
    }
    val notes = vm.progressNotes.collectAsState()
    Text("Progress Notes")
    if (notes.value.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(notes.value) { note ->

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
//                     .size(width = 240.dp, height = 100.dp)
                        .fillMaxWidth(0.95f),
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(note.user)
                        Spacer(Modifier.weight(1f))
                        Text("Date: ${note.dateCreated}")
                    }
                    Text("Note:  ${note.note}")
                }


            }
        }
}

    } else {
        Text("No Notes")
    }


}