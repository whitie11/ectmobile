package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun MyInputTextField(
    labelValue: String,
    imageRes: DrawableResource,
    iconDescription: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier,
    initialText: String = ""
) {
    var textValue by rememberSaveable {
        mutableStateOf(initialText)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        value = textValue,
        onValueChange = {
            textValue = it
            onTextChanged(it)
        },
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions.Default,
        leadingIcon = {
            Icon(painterResource(imageRes), contentDescription = iconDescription)
        }
    )
}