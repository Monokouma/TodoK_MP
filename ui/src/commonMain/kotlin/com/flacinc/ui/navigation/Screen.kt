package com.flacinc.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object CreateMeeting : Screen("createMeeting")
    object MeetingDetails : Screen("meetingDetails/{meetingId}") {
        fun createRoute(meetingId: Long) = "meetingDetails/$meetingId"
    }
}