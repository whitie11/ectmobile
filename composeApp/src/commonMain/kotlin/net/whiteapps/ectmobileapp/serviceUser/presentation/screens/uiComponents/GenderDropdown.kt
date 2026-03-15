package net.whiteapps.ectmobileapp.serviceUser.presentation.screens.uiComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.whiteapps.ectmobileapp.serviceUser.presentation.events.NewSUFormEvent
import net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels.NewSUViewModel
import net.whiteapps.enums.Gender
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GenderDropdown(modifier: Modifier) {
    val expanded = remember { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf<Gender?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text("Gender")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    expanded.value = true
                }
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primary
                ),
            contentAlignment = Alignment.CenterStart


        ) {
            if (selectedOption == null){
                Text("Click to select gender", modifier = Modifier.padding(start = 16.dp))
            }  else {
                Text(selectedOption!!.name, modifier = Modifier.padding(start = 16.dp))

            }

            DropdownMenu(
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                val viewModel = koinViewModel<NewSUViewModel>()
                enumValues<Gender>().forEach{  selectionOption ->
                    DropdownMenuItem(

                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            selectedOption = selectionOption
                            expanded.value = false
                            viewModel.onEvent(NewSUFormEvent.GenderChanged(selectionOption))
                        },
                        text = {
                            Text(text = selectionOption.name)
                        }
                    )
                }
            }
        }
    }
}