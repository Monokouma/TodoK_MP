package com.flacinc.todok_mp.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flacinc.todok_mp.ui.home.model.UiMeeting
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.filter
import todok_mp.composeapp.generated.resources.home_no_meeting_subtitle
import todok_mp.composeapp.generated.resources.home_no_meeting_title
import todok_mp.composeapp.generated.resources.home_top_bar_title
import todok_mp.composeapp.generated.resources.meeting_start_at
import todok_mp.composeapp.generated.resources.participants_number
import todok_mp.composeapp.generated.resources.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.onSurface),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.home_top_bar_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painterResource(Res.drawable.filter),
                                contentDescription = "Filter",
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("A - Z") },
                                onClick = {
                                    // Action
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text("Z - A")
                                },
                                onClick = {
                                    // Action
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text("Plus récent")
                                },
                                onClick = {
                                    // Action
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text("Plus ancien")
                                },
                                onClick = {
                                    // Action
                                    expanded = false
                                }
                            )
                        }
                    }

                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {

            FloatingActionButton(
                onClick = { onFabClick() },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(Res.drawable.plus),
                    contentDescription = "Plus",
                    modifier = Modifier.size(60.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                )
            }
        }
    ) { paddingValues ->
        when (val state = uiState.value) {
            HomeUiState.Loading -> {
                HomeLoadingContent(paddingValues)
            }

            HomeUiState.Error -> {
                HomeErrorContent(
                    paddingValues
                )
            }

            is HomeUiState.ShowMeetings -> {
                HomeWithMeetingsContent(
                    paddingValues,
                    meetingList = state.meetings
                )
            }

            HomeUiState.EmptyMeetings -> {
                HomeWithoutMeetingsContent(
                    paddingValues
                )
            }
        }
    }
}

@Composable
private fun HomeErrorContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/error.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 0.4f
    )

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Une erreur est survenue",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress }
            ),
            contentDescription = "Animation",
            modifier = Modifier.size(200.dp)
        )
    }
}


@Composable
private fun HomeWithoutMeetingsContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/no_meetings.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 0.8f
    )

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.home_no_meeting_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.home_no_meeting_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress }
            ),
            contentDescription = "Animation",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
private fun HomeLoadingContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            strokeWidth = 4.dp,
            trackColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun HomeWithMeetingsContent(
    paddingValues: PaddingValues,
    meetingList: PersistentList<UiMeeting>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
        ) {
            items(meetingList.size) { index ->
                val meeting = meetingList[index]
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = meeting.place.colorResource.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Spacer(Modifier.height(8.dp))

                            Text(
                                meeting.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Start,
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                stringResource(Res.string.participants_number, meeting.participants.size),
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                stringResource(Res.string.meeting_start_at, meeting.timestamp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.height(8.dp))

                        }
                        Spacer(Modifier.weight(1f))
                        Column {

                            Box(
                                modifier = modifier
                                    .shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(16.dp),
                                        spotColor = Color.Black
                                    )
                                    .background(
                                        color = meeting.place.colorResource,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 16.dp).padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    stringResource(meeting.place.resourceNameId),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                            }

                        }

                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    TodoKMPTheme {
        HomeWithMeetingsContent(
            paddingValues = PaddingValues(0.dp),
            provideListOfMeetingEntity().toPersistentList()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreviewNight() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        HomeWithMeetingsContent(
            paddingValues = PaddingValues(0.dp),
            provideListOfMeetingEntity().toPersistentList()
        )
    }
}

private fun provideListOfMeetingEntity() = List(3) {
    UiMeeting(
        id = it.toLong(),
        title = "Daily+$it",
        subject = "Standup+$it",
        timestamp = "14:30",
        place = MeetingPlace.entries[it],
        participants = persistentListOf("Alice", "Bob"),
    )
}