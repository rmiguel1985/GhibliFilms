package com.example.ghiblifilms.features.films.data.datasources.disk

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel

interface GhibliFilmsDiskDatasource {
    suspend fun getGhibliFilmsList(): Result<List<FilmModel>>
    suspend fun setGhibliFilmsList(filmsList: List<FilmModel>)
}
