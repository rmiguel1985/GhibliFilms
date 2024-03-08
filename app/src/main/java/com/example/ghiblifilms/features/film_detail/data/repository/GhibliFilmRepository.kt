package com.example.ghiblifilms.features.film_detail.data.repository

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GhibliFilmRepository {
    suspend fun getFilm(filmId: String): Result<FilmModel>
    suspend fun getTrailer(filmName: String): Result<Trailer>
}
