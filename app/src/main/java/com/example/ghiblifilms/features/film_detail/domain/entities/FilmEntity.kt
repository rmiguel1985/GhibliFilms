package com.example.ghiblifilms.features.film_detail.domain.entities

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

data class FilmEntity(
    val title: String = "",
    val score: String = "",
    val description: String = "",
    val movieBanner: String = "",
    val youtubeVideoId: String = "",
    val originalTitle: String = "",
    val originalTitleRomanised: String = ""
)

fun FilmModel.transformToEntity(youtubeVideoId: String): FilmEntity =
    FilmEntity(
        title = title,
        score = rtScore,
        description = description,
        movieBanner = movieBanner,
        youtubeVideoId = youtubeVideoId,
        originalTitle = originalTitle,
        originalTitleRomanised = originalTitleRomanised
    )


