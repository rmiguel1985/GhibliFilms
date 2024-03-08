package com.example.ghiblifilms.features.films.data.datasources.disk.schema

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ghiblifilms.features.films.data.datasources.disk.dao.GhibliFilmsDao
import com.example.ghiblifilms.utils.DATABASE_VERSION

@Database(entities = [RoomFilmModel::class],
    version = DATABASE_VERSION,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun GhibliFilmsDao(): GhibliFilmsDao
}
