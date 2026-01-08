package com.flacinc.todok_mp.di

import com.flacinc.ui.create_meeting.CreateMeetingViewModel
import com.flacinc.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { CreateMeetingViewModel(get()) }
}