package com.flacinc.todok_mp.di

import com.flacinc.ui.create_meeting.CreateMeetingViewModel
import com.flacinc.ui.home.HomeViewModel
import com.flacinc.ui.meeting_details.MeetingDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModel(getMeetingsUseCase = get(), deleteOldMeetingUseCase = get()) }
    viewModel { CreateMeetingViewModel(createMeetingUseCase = get()) }
    viewModel { MeetingDetailsViewModel(getMeetingByIdUseCase = get()) }
}