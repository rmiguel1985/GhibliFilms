package com.example.ghiblifilms.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.ghiblifilms.di.DatabaseModule
import com.example.ghiblifilms.di.HttpClientModule
import com.example.ghiblifilms.features.film_detail.di.FilmDetailModule
import com.example.ghiblifilms.features.films.di.FilmstModule
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val koinModules: MutableList<Module> =
            mutableListOf(HttpClientModule, DatabaseModule, FilmDetailModule, FilmstModule)

        startKoin {
            androidLogger(Level.INFO)
            androidContext(context)
            modules(koinModules)

            Timber.d("Koin is initialized")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(TimberInitializer::class.java)
}


