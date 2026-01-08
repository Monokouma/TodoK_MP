package com.flacinc.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flacinc.ui.create_meeting.CreateMeetingScreen
import com.flacinc.ui.home.HomeScreen
import com.flacinc.ui.meeting_details.MeetingDetailsScreen
import com.flacinc.ui.navigation.Screen
import com.flacinc.ui.splash.SplashScreen


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
                },
                onMeetingClick = {
                    navController.navigate(Screen.MeetingDetails.createRoute(it))
                }
            )
        }

        composable(Screen.CreateMeeting.route) {
            CreateMeetingScreen(
                onBackPress = {
                    navController.popBackStack()
                },
                onSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            Screen.MeetingDetails.route,
            arguments = listOf(
                navArgument("meetingId") { type = NavType.LongType }
            )
        ) {
            val itemId =
                navController.currentBackStackEntry?.savedStateHandle?.get<Long>("meetingId")
                    ?: return@composable

            MeetingDetailsScreen(
                onBackPress = {
                    navController.popBackStack()
                },
                meetingId = itemId
            )
        }
    }
}