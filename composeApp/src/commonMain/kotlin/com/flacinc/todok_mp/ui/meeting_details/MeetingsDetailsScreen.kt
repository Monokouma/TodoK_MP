package com.flacinc.todok_mp.ui.meeting_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MeetingDetailsScreen(
    meetingId: Long,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,

    ) {

    LaunchedEffect(Unit) {
        println(meetingId)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(meetingId.toString(), color = Color.White)
    }
}