package com.example.ghiblifilms.features.film_detail.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.ghiblifilms.ui.common.ArrowBackUpIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenAppBar(title: String, onUpClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            ArrowBackUpIcon(
                onUpClick = onUpClick
            )
        },
        title = { Text(text = title) },
    )
}
