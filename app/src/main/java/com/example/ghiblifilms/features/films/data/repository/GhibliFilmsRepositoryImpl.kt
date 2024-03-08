package com.example.ghiblifilms.features.films.data.repository

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsPolicy

class GhibliFilmsRepositoryImpl(private val ghibliFilmsPolicy: GhibliFilmsPolicy) :
    GhibliFilmsRepository {
    override suspend fun getFilms(): Result<List<FilmModel>> = ghibliFilmsPolicy.getGhibliFilms()
}
