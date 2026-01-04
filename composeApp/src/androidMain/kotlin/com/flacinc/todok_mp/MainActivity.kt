package com.flacinc.todok_mp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flacinc.todok_mp.ui.TodokMpApp
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme

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
