package com.example.ghiblifilms.features.films.data.datasources.disk

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.datasources.disk.dao.GhibliFilmsDao
import com.example.ghiblifilms.features.films.data.datasources.disk.mapper.transformFromModelToRoom
import com.example.ghiblifilms.features.films.data.datasources.disk.mapper.transformFromRoomToModel

class GhibliFilmsDiskDatasourceImpl(private val ghibliFilmsRoomDao: GhibliFilmsDao): GhibliFilmsDiskDatasource {
    override suspend fun getGhibliFilmsList(): Result<List<FilmModel>> {
        val filmsList = ghibliFilmsRoomDao.getAll()

        return if (filmsList.isNotEmpty()) {
            Result.success(filmsList.transformFromRoomToModel())
        } else {
            Result.failure(Exception("Error getting films from disk"))
        }
    }

    override suspend fun setGhibliFilmsList(filmsList: List<FilmModel>) {
        ghibliFilmsRoomDao.createAll(filmsList.transformFromModelToRoom())
    }
}
