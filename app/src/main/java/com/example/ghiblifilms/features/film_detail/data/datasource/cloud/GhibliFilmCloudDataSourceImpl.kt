package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import com.example.ghiblifilms.BuildConfig
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

class GhibliFilmCloudDataSourceImpl(
    private val filmService: GhibliFilmService,
    private val trailerService: GhibliTrailerService
) : GhibliFilmCloudDataSource {
    override suspend fun getFilm(filmId: String): Result<FilmModel> = runCatching {
        filmService.getGhibliFilm(filmId)
    }

    override suspend fun getTrailer(filmName: String): Result<Trailer> = runCatching {
        val titleQuery = String.format(YOUTUBE_TRAILER_SEARCH_QUERY, filmName)
        trailerService.getTrailerUrl(
            filmTitle = titleQuery,
            youtubeApiKey = BuildConfig.YOUTUBE_API_KEY
        )
    }

    companion object {
        private const val YOUTUBE_TRAILER_SEARCH_QUERY = "%s trailer studio ghibli"
    }
}

