package net.whiteapps.ectmobileapp

import androidx.compose.ui.window.ComposeUIViewController
import net.whiteapps.ectmobileapp.common.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }