package com.example.ghiblifilms.features.films.data.datasources.cloud

class GhibliFilmsCloudDataSourceImpl(private val service: GhibliFilmsService) :
    GhibliFilmsCloudDataSource {
    override suspend fun getGhibliFilms(): Result<List<FilmModel>> = runCatching {
        service.getGhibliFilms()
    }
}
