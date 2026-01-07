package com.flacinc.todok_mp.ui.create_meeting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme
import com.flacinc.todok_mp.ui.utils.millisToTimeString
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import com.flacinc.todok_mp.ui.utils.timeStringToMillis
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.arrow_back
import todok_mp.composeapp.generated.resources.create_meeting_title
import todok_mp.composeapp.generated.resources.edit
import todok_mp.composeapp.generated.resources.person
import kotlin.time.Clock
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMeetingScreen(
    onBackPress: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateMeetingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    state.errorMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    LaunchedEffect(onSuccess) {
        viewModel.onSuccess.collect {
            onSuccess()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.onSurface),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(Res.string.create_meeting_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        painterResource(Res.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(40.dp).clickable {
                            onBackPress()
                        }.padding(horizontal = 8.dp)
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        CreateMeetingContent(
            paddingValues = paddingValues,
            state = state,
            onSubjectChange = { viewModel.updateSubject(it) },
            onMeetingPlaceChange = { viewModel.updateMeetingPlace(it) },
            onTimestampChange = { viewModel.updateTimestamp(it) },
            onTitleChange = { viewModel.updateTitle(it) },
            onSubmit = { viewModel.submit() },
            onParticipantAdd = { viewModel.updateParticipants(it) },
        )
    }
}

@Composable
fun CreateMeetingContent(
    paddingValues: PaddingValues,
    state: CreateMeetingFormState,
    onSubjectChange: (String) -> Unit,
    onMeetingPlaceChange: (MeetingPlace) -> Unit,
    onTimestampChange: (Long) -> Unit,
    onTitleChange: (String) -> Unit,
    onParticipantAdd: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MeetingTitleField(
            value = state.title,
            onValueChange = onTitleChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        MeetingSubjectField(
            value = state.subject,
            onValueChange = onSubjectChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        MeetingParticipantsField(
            participants = state.participants,
            onParticipantAdd = onParticipantAdd
        )
        Spacer(modifier = Modifier.height(8.dp))
        MeetingRoom(
            meetingPlaceValue = state.meetingPlace,
            onMeetingPlaceSelect = { onMeetingPlaceChange(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        MeetingSchedule(
            timestamp = state.timestamp,
            onTimestampChange = {
                onTimestampChange(it)
            }
        )
        MeetingSubmitButton(
            onSubmit = onSubmit
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun MeetingSubmitButton(
    onSubmit: () -> Unit
) {
    Button(
        onClick = {
            onSubmit()
        },
        modifier = Modifier.padding(8.dp),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Gray,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Text("Submit")
    }
}

@Composable
private fun MeetingSchedule(
    timestamp: Long,
    onTimestampChange: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Début de la réunion",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "La durée d'une réunion est de 30 minutes",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(8.dp))
        TimeDropdown(
            selectedTime = millisToTimeString(timestamp),
            onTimeSelect = { onTimestampChange(timeStringToMillis(it)) },
            label = "Heure de début",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDropdown(
    selectedTime: String,
    onTimeSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Start Time",
    minHour: Int = 8,
    maxHour: Int = 18
) {
    var expanded by remember { mutableStateOf(false) }

    val currentHour = remember {
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .hour
    }

    val timeSlots = remember(currentHour) {
        buildList {
            val startHour = maxOf(minHour, currentHour)
            for (hour in startHour..maxHour) {
                for (minute in listOf(0, 30)) {
                    if (hour == currentHour) {
                        val currentMinute = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .minute
                        if (minute < currentMinute) continue
                    }
                    add("${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}")
                }
            }
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            timeSlots.forEach { time ->
                DropdownMenuItem(
                    text = { Text(time) },
                    onClick = {
                        onTimeSelect(time)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun MeetingRoom(
    meetingPlaceValue: MeetingPlace,
    onMeetingPlaceSelect: (MeetingPlace) -> Unit,
    modifier: Modifier = Modifier
) {
    val place = MeetingPlace.entries.toImmutableList()

    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        place.forEach {

            Button(
                onClick = {
                    onMeetingPlaceSelect(it)
                },
                modifier = Modifier.padding(8.dp),
                colors = ButtonColors(
                    containerColor = if (it.name == meetingPlaceValue.name) {
                        meetingPlaceValue.colorResource
                    } else {
                        Color.Gray
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            ) {
                Text(stringResource(it.resourceNameId))
            }


        }
    }

}

@Composable
fun MeetingTitleField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                "Titre de la réunion",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        placeholder = {
            Text(
                "Ex: Point hebdomadaire",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.edit),
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
fun MeetingSubjectField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                "Sujet de la réunion",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        placeholder = {
            Text(
                "Ex: Voir les points bloquants du jour",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.edit),
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        ),
        singleLine = false,
        maxLines = 4,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
fun MeetingParticipantsField(
    participants: ImmutableList<String>,
    onParticipantAdd: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = {
                Text(
                    "Ajouter un participant",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            placeholder = {
                Text(
                    "Ex: Jean Dupont",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = "Person",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (input.isNotBlank()) {
                        onParticipantAdd(input)
                        input = ""
                    }
                }
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        if (participants.isNotEmpty()) {
            Text(
                text = participants.joinToString(", "),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .horizontalScroll(rememberScrollState())
            )
        }
    }
}


@Preview
@Composable
private fun CreateMeetingScreenPreview() {
    TodoKMPTheme {
        CreateMeetingContent(
            paddingValues = PaddingValues(0.dp),
            state = CreateMeetingFormState(
                timestamp = 1704545400000L,
                meetingPlace = MeetingPlace.ROOM_200,
                subject = "",
                title = "",
                participants = persistentListOf(),
                errorMessage = null,
                isLoading = false
            ),
            onSubjectChange = { },
            onMeetingPlaceChange = { },
            onTimestampChange = { },
            onTitleChange = { },
            onSubmit = { },
            onParticipantAdd = { },
        )
    }
}

@Composable
@Preview
private fun CreateMeetingScreenPreviewNight() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        CreateMeetingContent(
            paddingValues = PaddingValues(0.dp),
            state = CreateMeetingFormState(
                timestamp = 1704545400000L,
                meetingPlace = MeetingPlace.ROOM_500,
                subject = "",
                title = "",
                participants = persistentListOf(
                    "JeanDupont@lamzone.fr",
                    "JeanDupont@lamzone.fr",
                    "JeanDupont@lamzone.fr",
                    "JeanDupont@lamzone.fr",
                    "JeanDupont@lamzone.fr"
                ),
                errorMessage = null,
                isLoading = false
            ),
            onSubjectChange = { },
            onMeetingPlaceChange = { },
            onTimestampChange = { },
            onTitleChange = { },
            onSubmit = { },
            onParticipantAdd = { },

            )
    }
}