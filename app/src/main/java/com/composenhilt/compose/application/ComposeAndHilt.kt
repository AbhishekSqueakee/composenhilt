package com.composenhilt.compose.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeAndHilt : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}