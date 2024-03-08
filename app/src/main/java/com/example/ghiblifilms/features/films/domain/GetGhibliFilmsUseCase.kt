package com.example.ghiblifilms.features.films.domain

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GetGhibliFilmsUseCase {
    suspend fun execute(): Result<List<FilmModel>>
}
