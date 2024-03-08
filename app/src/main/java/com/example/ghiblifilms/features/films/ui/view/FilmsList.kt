package com.example.ghiblifilms.features.films.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.ghiblifilms.R
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.ui.theme.padding_150
import com.example.ghiblifilms.ui.theme.padding_4
import com.example.ghiblifilms.ui.theme.padding_8


@Composable
fun GhibliFilmList(
    filmsList: List<FilmModel>,
    onMediaClick: (FilmModel) -> Unit,
    modifier: Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.testTag("filmsList"),
        columns = GridCells.Adaptive(padding_150),
        contentPadding = PaddingValues(padding_4)
    ) {
        items(filmsList) {
            GhibliFilmListItem(
                ghibliItem = it,
                onClick = { onMediaClick(it) },
            )
        }
    }
}

@Composable
fun GhibliFilmListItem(
    ghibliItem: FilmModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(padding_8)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card {
            AsyncImage(
                model = ghibliItem.image,
                contentDescription = ghibliItem.title,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_image_error),
                modifier = Modifier
                    .background(Color.LightGray)
            )
        }

        Text(
            text = ghibliItem.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            modifier = Modifier
                .padding(padding_8, padding_8)
        )
    }
}
