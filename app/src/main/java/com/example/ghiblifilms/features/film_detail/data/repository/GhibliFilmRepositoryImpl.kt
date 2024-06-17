package com.example.ghiblifilms.features.film_detail.data.repository

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliFilmCloudDataSource
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

class GhibliFilmRepositoryImpl(
    private val ghibliFilmCloudDataSource: GhibliFilmCloudDataSource
) : GhibliFilmRepository {
    override suspend fun getFilm(filmId: String): Result<FilmModel> = ghibliFilmCloudDataSource.getFilm(filmId)

    override suspend fun getTrailer(filmName: String): Result<Trailer> = ghibliFilmCloudDataSource.getTrailer(filmName)
}
