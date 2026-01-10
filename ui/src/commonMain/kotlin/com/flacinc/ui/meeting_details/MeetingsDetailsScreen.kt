package com.flacinc.ui.meeting_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flacinc.ui.model.UiMeeting
import com.flacinc.ui.resources.Res
import com.flacinc.ui.resources.arrow_back
import com.flacinc.ui.theme.TodoKMPTheme
import com.flacinc.ui.utils.model.MeetingPlace
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailsScreen(
    meetingId: Long,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MeetingDetailsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(meetingId) {
        viewModel.onDetailsLoad(meetingId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Détails de la réunion") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPress() },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back),
                            contentDescription = "Back",
                            Modifier.size(32.dp).padding(start = 8.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState.value) {

            MeetingDetailsState.Error -> {
                MeetingDetailsError(paddingValues)
            }

            MeetingDetailsState.Loading -> {
                MeetingDetailsLoading(paddingValues)
            }

            is MeetingDetailsState.MeetingDetails -> {
                MeetingDetailsContent(

                    paddingValues,
                    state.meetingDetails,

                    )
            }
        }
    }
}

@Composable
fun MeetingDetailsLoading(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            strokeWidth = 4.dp,
            trackColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun MeetingDetailsError(
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress }
            ),
            contentDescription = "Animation",
            modifier = Modifier.size(200.dp)
        )

        Text(
            "Impossible de récupérer les détails de la réunion",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MeetingDetailsContent(
    paddingValues: PaddingValues,
    uiMeeting: UiMeeting,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlassCard(
            color = uiMeeting.room.colorResource,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(uiMeeting.room.resourceNameId),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    uiMeeting.timestamp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }


        Text(
            uiMeeting.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            textAlign = TextAlign.Center
        )


        Text(
            uiMeeting.subject,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiMeeting.participants) { participant ->
                ElevatedCard(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = participant,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                }
            }
        }

        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun GlassCard(
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .blur(radius = 32.dp)
                .background(color.copy(alpha = 0.36f))
                .border(
                    width = 2.dp,
                    color = color.copy(alpha = 0.8f),
                ).fillMaxWidth()
                .padding(60.dp)
        )
        Column {
            content()
        }
    }
}

@Composable
@Preview
private fun MeetingDetailsScreenPreview() {
    TodoKMPTheme(
        darkTheme = true
    ) {

        MeetingDetailsContent(
            paddingValues = PaddingValues(),
            uiMeeting = UiMeeting(
                id = 1L,
                title = "Daily",
                subject = "Standup",
                timestamp = "14:30",
                room = MeetingPlace.ROOM_500,
                participants = persistentListOf(
                    "Alice", "Bob", "Charlie", "Diana",
                    "Émile", "Fatou", "Gabriel", "Hugo",
                    "Inès", "Jules", "Karim", "Léa"
                )
            )
        )
    }
}


@Composable
@Preview
private fun MeetingLoadingScreenPreview() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        MeetingDetailsLoading(
            paddingValues = PaddingValues(),
        )
    }
}

@Composable
@Preview
private fun MeetingErrorScreenPreview() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        MeetingDetailsError(
            paddingValues = PaddingValues(),
        )
    }
}

