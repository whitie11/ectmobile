package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenTextbox(

    text: String = "Text to display in the textbox",
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),

    ) {
        Card(
            modifier = Modifier
//                .padding(16.dp)
                .fillMaxSize(),
//            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
            ) {
                Text( text= text,
                    Modifier.padding(8.dp),
                   )
            }
            Button(onClick = {
                onCancel()
            }) {
                Text("Close!")
            }

        }


    }
}