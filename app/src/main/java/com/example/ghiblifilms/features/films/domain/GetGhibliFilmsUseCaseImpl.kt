package com.example.ghiblifilms.features.films.domain

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.repository.GhibliFilmsRepository

class GetGhibliFilmsUseCaseImpl(private val ghibliFilmsRepository: GhibliFilmsRepository) :
    GetGhibliFilmsUseCase {
    override suspend fun execute(): Result<List<FilmModel>> = ghibliFilmsRepository.getFilms()
}
