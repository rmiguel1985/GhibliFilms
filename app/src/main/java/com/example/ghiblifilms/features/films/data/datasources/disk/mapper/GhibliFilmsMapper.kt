package com.example.ghiblifilms.features.films.data.datasources.disk.mapper

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.datasources.disk.schema.RoomFilmModel

fun List<RoomFilmModel>.transformFromRoomToModel(): List<FilmModel> {
    val filmsList = mutableListOf<FilmModel>()

    forEach { roomFilmModel ->
        val filmModel = FilmModel()

        filmModel.id = roomFilmModel.id
        filmModel.title = roomFilmModel.title
        filmModel.originalTitle = roomFilmModel.originalTitle
        filmModel.originalTitleRomanised = roomFilmModel.originalTitleRomanised
        filmModel.description = roomFilmModel.description

        filmsList.add(filmModel)
    }

    return filmsList
}

fun List<FilmModel>.transformFromModelToRoom(): List<RoomFilmModel> {
    val roomFilmsList = mutableListOf<RoomFilmModel>()

    forEach { filmsList ->
        val roomFilm = RoomFilmModel()

        roomFilm.id = filmsList.id
        roomFilm.title = filmsList.title
        roomFilm.originalTitle = filmsList.originalTitle
        roomFilm.originalTitleRomanised = filmsList.originalTitleRomanised
        roomFilm.description = filmsList.description

        roomFilmsList.add(roomFilm)
    }

    return roomFilmsList
}

