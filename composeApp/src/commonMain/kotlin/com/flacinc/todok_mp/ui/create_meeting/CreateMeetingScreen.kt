package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flacinc.todok_mp.ui.home.HomeViewModel
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateMeetingScreen(
    viewModel: CreateMeetingViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {


}

@Preview
@Composable
private fun CreateMeetingScreenPreview() {
    TodoKMPTheme {
        CreateMeetingScreen()
    }
}

@Preview
@Composable
private fun CreateMeetingScreenPreviewNight() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        CreateMeetingScreen()
    }
}