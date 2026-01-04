package com.flacinc.todok_mp

import androidx.compose.ui.window.ComposeUIViewController
import com.flacinc.todok_mp.di.initKoin
import com.flacinc.todok_mp.ui.TodokMpApp
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    TodoKMPTheme {
        TodokMpApp()
    }
}