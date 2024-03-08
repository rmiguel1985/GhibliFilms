
package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliFilmService {
    @GET("/films/{filmId}")
    suspend fun getGhibliFilm(
        @Path("filmId") filmId: String
    ): FilmModel
}
