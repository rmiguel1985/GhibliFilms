package com.example.ghiblifilms.features.films.data.datasources.cloud

import retrofit2.http.GET

interface GhibliFilmsService {
    @GET("/films")
    suspend fun getGhibliFilms(): List<FilmModel>
}
