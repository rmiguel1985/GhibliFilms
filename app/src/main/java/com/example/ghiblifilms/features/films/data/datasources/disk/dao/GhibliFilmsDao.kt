package com.example.ghiblifilms.features.films.data.datasources.disk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghiblifilms.features.films.data.datasources.disk.schema.RoomFilmModel
import com.example.ghiblifilms.utils.DATABASE_GHIBLI_FILMS_TABLE_NAME

@Dao
interface GhibliFilmsDao {
    @Query("SELECT * FROM $DATABASE_GHIBLI_FILMS_TABLE_NAME")
    suspend fun getAll(): List<RoomFilmModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAll(films: List<RoomFilmModel>)
}

