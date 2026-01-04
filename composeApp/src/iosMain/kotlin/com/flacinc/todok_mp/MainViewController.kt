package com.flacinc.todok_mp

import androidx.compose.ui.window.ComposeUIViewController
import com.flacinc.todok_mp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {

}