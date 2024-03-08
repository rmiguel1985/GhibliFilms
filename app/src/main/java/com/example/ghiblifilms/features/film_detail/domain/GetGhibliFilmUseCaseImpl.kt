package com.example.ghiblifilms.features.film_detail.domain

import com.example.ghiblifilms.features.film_detail.data.repository.GhibliFilmRepository
import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity
import com.example.ghiblifilms.features.film_detail.domain.entities.transformToEntity

class GetGhibliFilmUseCaseImpl(private val getGhibliFilmUseCaseImpl: GhibliFilmRepository) : GetGhibliFilmUseCase {
    override suspend fun execute(filmId: String): Result<FilmEntity> =
        getGhibliFilmUseCaseImpl.getFilm(filmId).map {
            val trailer = getGhibliFilmUseCaseImpl.getTrailer(it.title)
            it.transformToEntity(
                youtubeVideoId = trailer.getOrNull()?.run { items.first().id.videoId } ?: "")
        }
}

