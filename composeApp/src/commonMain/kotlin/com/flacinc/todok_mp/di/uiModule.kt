package com.flacinc.todok_mp.di

import com.flacinc.todok_mp.ui.create_meeting.CreateMeetingViewModel
import com.flacinc.todok_mp.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CreateMeetingViewModel(get()) }
}