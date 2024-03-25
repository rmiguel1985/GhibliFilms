package com.example.ghiblifilms.features.films.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.ui.GhibliFilmsViewModel
import com.example.ghiblifilms.ui.common.ErrorScreen
import com.example.ghiblifilms.ui.common.GhibliFilmsApp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onMediaClick: (FilmModel) -> Unit) {
    val viewModel = koinViewModel<GhibliFilmsViewModel>()
    val state by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getGhibliFilms()
            pullRefreshState.endRefresh()
        }
    }

    GhibliFilmsApp {
        Scaffold(
            topBar = { MainAppBar() }
        ) { padding ->
            Content(
                modifier = Modifier.padding(padding),
                loading = state.loading,
                filmsList = state.filmList,
                error = state.error,
                pullRefreshState = pullRefreshState,
                onTryAgainClick = { viewModel.getGhibliFilms() },
                onMediaClick = onMediaClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    loading: Boolean,
    filmsList: List<FilmModel>,
    error: String,
    onTryAgainClick: () -> Unit,
    onMediaClick: (FilmModel) -> Unit,
    pullRefreshState: PullToRefreshState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection),
        contentAlignment = Alignment.Center
    ) {
        when {
            loading -> CircularProgressIndicator()

            filmsList.isNotEmpty() ->
                GhibliFilmList(
                    filmsList = filmsList,
                    onMediaClick = onMediaClick,
                    modifier = modifier
                )

            error.isNotEmpty() -> ErrorScreen(errorMessage = error, onclick = onTryAgainClick)
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullRefreshState,
        )
    }
}
