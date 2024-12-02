package com.composenhilt.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.composenhilt.compose.navigation.Navigation
import com.composenhilt.ui.theme.ComposeNHiltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNHiltTheme {
                Navigation()
            }
        }
    }
}