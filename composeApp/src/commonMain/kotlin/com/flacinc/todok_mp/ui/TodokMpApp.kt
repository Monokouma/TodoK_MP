package com.flacinc.todok_mp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flacinc.todok_mp.ui.create_meeting.CreateMeetingScreen
import com.flacinc.todok_mp.ui.home.HomeScreen
import com.flacinc.todok_mp.ui.meeting_details.MeetingDetailsScreen
import com.flacinc.todok_mp.ui.navigation.Screen
import com.flacinc.todok_mp.ui.splash.SplashScreen

@Composable
fun TodokMpApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onFabClick = {
                    navController.navigate(Screen.CreateMeeting.route)
                }
            )
        }

        composable(Screen.CreateMeeting.route) {
            CreateMeetingScreen(
                onBackPress = {
                    navController.popBackStack()
                },
                onSuccess = {
                    navController.navigate(Screen.MeetingDetails.route)
                }
            )
        }

        composable(
            Screen.MeetingDetails.route,
        ) {
            MeetingDetailsScreen()
        }
    }
}