package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GhibliFilmCloudDataSource {
    suspend fun getFilm(filmId: String): Result<FilmModel>
    suspend fun getTrailer(filmName: String): Result<Trailer>
}
