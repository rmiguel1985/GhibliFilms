package com.example.ghiblifilms.features.film_detail.domain

import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity

interface GetGhibliFilmUseCase {
    suspend fun execute(filmId: String): Result<FilmEntity>
}


