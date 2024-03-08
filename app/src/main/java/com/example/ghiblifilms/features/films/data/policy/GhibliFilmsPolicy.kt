package com.example.ghiblifilms.features.films.data.policy

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GhibliFilmsPolicy {
    suspend fun getGhibliFilms(): Result<List<FilmModel>>
}

