package com.example.ghiblifilms.features.film_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ghiblifilms.R
import com.example.ghiblifilms.ui.common.YouTubePlayer
import com.example.ghiblifilms.ui.common.ErrorScreen
import com.example.ghiblifilms.ui.common.GhibliFilmsApp
import com.example.ghiblifilms.ui.theme.padding_50
import com.example.ghiblifilms.ui.theme.padding_8
import com.example.ghiblifilms.ui.theme.roundedCorner_10
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    onUpClick: () -> Unit = {},
    viewModel: GhibliFilmDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    GhibliFilmsApp {
        Scaffold(
            topBar = {
                DetailScreenAppBar(
                    title = state.film.title,
                    onUpClick = onUpClick
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.error.isNotEmpty() ->
                        ErrorScreen(
                            errorMessage = state.error,
                            onclick = {
                                viewModel.getFilm()
                            }
                        )

                    state.loading -> CircularProgressIndicator()

                    else -> Content(state)
                }
            }
        }
    }
}

@Composable
fun Content(state: GhibliFilmDetailViewModel.DetailUiState) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = state.film.movieBanner,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_banner_error),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.padding(padding_8))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(padding_50)
                    .padding(padding_8)
                    .clip(RoundedCornerShape(roundedCorner_10))
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.film.score)
            }

            Text(
                text = "${state.film.originalTitle} - ${state.film.originalTitleRomanised}",
                modifier = Modifier.padding(padding_8)
            )
        }

        Text(text = state.film.description, modifier = Modifier.padding(padding_8))

        YouTubePlayer(
            youtubeVideoId = state.film.youtubeVideoId,
            lifecycleOwner = LocalLifecycleOwner.current
        )
    }
}
