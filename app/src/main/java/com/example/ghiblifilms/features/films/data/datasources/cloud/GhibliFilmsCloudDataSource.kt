package com.example.ghiblifilms.features.films.data.datasources.cloud

interface GhibliFilmsCloudDataSource {
    suspend fun getGhibliFilms(): Result<List<FilmModel>>
}
