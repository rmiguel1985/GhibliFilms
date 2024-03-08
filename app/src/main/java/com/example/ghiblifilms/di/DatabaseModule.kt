package com.example.ghiblifilms.di

import android.content.Context
import androidx.room.Room
import com.example.ghiblifilms.features.films.data.datasources.disk.schema.AppDatabase
import com.example.ghiblifilms.utils.DATABASE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DatabaseModule = module {
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(database: AppDatabase) = database.GhibliFilmsDao()

    single { provideDatabase(context = androidContext()) }
    single { provideDao(database = get()) }
}
