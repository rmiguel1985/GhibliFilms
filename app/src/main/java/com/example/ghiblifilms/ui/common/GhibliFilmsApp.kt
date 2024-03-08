package com.example.ghiblifilms.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.ghiblifilms.ui.theme.GhibliFilmsTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GhibliFilmsApp(content: @Composable () -> Unit) {
    GhibliFilmsTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            KoinAndroidContext {
                content()
            }
        }
    }
}

