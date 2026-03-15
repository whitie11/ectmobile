package net.whiteapps.ectmobileapp.common.presentation.uiComponents
//
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ectmobileapp.composeapp.generated.resources.Res
import ectmobileapp.composeapp.generated.resources.hide_password
import ectmobileapp.composeapp.generated.resources.password
import ectmobileapp.composeapp.generated.resources.lock_person_24px
import ectmobileapp.composeapp.generated.resources.show_password
import ectmobileapp.composeapp.generated.resources.visibility_24px
import ectmobileapp.composeapp.generated.resources.visibility_off_24px

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource




@Composable
fun PasswordTextField(
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    initialText: String = ""
) {
    var password by rememberSaveable {
        mutableStateOf(initialText)
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        value = password,
        onValueChange = {
            password = it
            onTextChanged(it)
        },
        label = {
            Text(text = stringResource(Res.string.password))
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {

            Icon(
                painterResource(Res.drawable.lock_person_24px),
                contentDescription = stringResource(Res.string.password)
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible) {
                painterResource(Res.drawable.visibility_24px)
            } else {
                painterResource(Res.drawable.visibility_off_24px)
            }

            val description = if (passwordVisible) {
                stringResource(Res.string.hide_password)
            } else {
                stringResource(Res.string.show_password)
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(iconImage, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else
            PasswordVisualTransformation()
    )
}
