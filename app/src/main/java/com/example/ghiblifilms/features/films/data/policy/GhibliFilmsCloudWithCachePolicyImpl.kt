package com.example.ghiblifilms.features.films.data.policy

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSource
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasource
import com.example.ghiblifilms.utils.ConnectivityHelper

class GhibliFilmsCloudWithCachePolicyImpl(
    private val filmsCloudDataSource: GhibliFilmsCloudDataSource,
    private val filmsListDiskDatasource: GhibliFilmsDiskDatasource
) : GhibliFilmsPolicy {
    override suspend fun getGhibliFilms(): Result<List<FilmModel>> =
        if (ConnectivityHelper.isOnline) {
            filmsCloudDataSource.getGhibliFilms().fold(
                onSuccess = {
                    filmsListDiskDatasource.setGhibliFilmsList(it)
                    return@fold Result.success(it)
                },
                onFailure = {
                    return@fold Result.failure(it)
                }
            )
        } else {
            filmsListDiskDatasource.getGhibliFilmsList()
        }
}

