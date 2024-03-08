package com.example.ghiblifilms.features.films.data.datasources.disk.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghiblifilms.utils.DATABASE_GHIBLI_FILMS_TABLE_NAME

@Entity(tableName = DATABASE_GHIBLI_FILMS_TABLE_NAME)
data class RoomFilmModel(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var originalTitle: String = "",
    var originalTitleRomanised: String = "",
    var description: String = "",
    var rtScore: String = "",
)
