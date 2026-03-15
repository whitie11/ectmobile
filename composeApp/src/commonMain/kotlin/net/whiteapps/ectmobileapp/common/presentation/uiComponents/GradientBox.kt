package net.whiteapps.ectmobileapp.common.presentation.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF0D4C92)
val Tail600 = Color(0xFF59C180)

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
//        contentAlignment = Alignment.Center,
       modifier = modifier
       .background(brush = Brush.linearGradient(
           listOf(
               Blue, Blue, Tail600, Tail600
           )
       ))
    ){
        content()
    }
}