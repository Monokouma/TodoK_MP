package com.flacinc.todok_mp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.flacinc.ui.TodokMpApp
import com.flacinc.ui.theme.TodoKMPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            TodoKMPTheme {
                TodokMpApp()
            }
        }
    }
}
