package com.flacinc.todok_mp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.filter
import todok_mp.composeapp.generated.resources.home_no_meeting_subtitle
import todok_mp.composeapp.generated.resources.home_no_meeting_title
import todok_mp.composeapp.generated.resources.home_top_bar_title
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
                    meetingList = state.meetings.toImmutableList()
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
        modifier = modifier.padding(paddingValues).fillMaxSize(),
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
        iterations = Compottie.IterateForever
    )

    Column(
        modifier = modifier.padding(paddingValues).fillMaxSize(),
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
        modifier = modifier.padding(paddingValues).fillMaxSize(),
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
    meetingList: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(paddingValues)
    ) {

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    TodoKMPTheme {
        HomeErrorContent(
            paddingValues = PaddingValues(0.dp)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreviewNight() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        HomeErrorContent(
            paddingValues = PaddingValues(0.dp)
        )
    }
}