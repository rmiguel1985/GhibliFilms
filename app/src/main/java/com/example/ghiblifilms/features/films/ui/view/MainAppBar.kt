package com.example.ghiblifilms.features.films.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ghiblifilms.R
import com.example.ghiblifilms.ui.common.AppBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            AppBarAction(
                imageVector = Icons.Default.Search,
                onClick = {}
            )
        }
    )
}
