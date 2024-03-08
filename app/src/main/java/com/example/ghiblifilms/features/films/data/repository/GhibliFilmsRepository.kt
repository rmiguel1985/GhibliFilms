package com.example.ghiblifilms.features.films.data.repository

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GhibliFilmsRepository {
    suspend fun getFilms(): Result<List<FilmModel>>
}
