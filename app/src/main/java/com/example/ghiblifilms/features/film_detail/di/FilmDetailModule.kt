package com.example.ghiblifilms.features.film_detail.di

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliFilmCloudDataSource
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliFilmCloudDataSourceImpl
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliFilmService
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliTrailerService
import com.example.ghiblifilms.features.film_detail.data.repository.GhibliFilmRepository
import com.example.ghiblifilms.features.film_detail.data.repository.GhibliFilmRepositoryImpl
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCase
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCaseImpl
import com.example.ghiblifilms.features.film_detail.ui.GhibliFilmDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val FilmDetailModule = module {
    fun provideFilmService(retrofit: Retrofit): GhibliFilmService =
        retrofit.create(GhibliFilmService::class.java)


    fun provideTrailerService(retrofit: Retrofit): GhibliTrailerService =
        retrofit.create(GhibliTrailerService::class.java)

    single<GhibliFilmService> {
        provideFilmService(get())
    }

    single<GhibliTrailerService> {
        provideTrailerService(get(named("youtube")))
    }

    single<GhibliFilmCloudDataSource> {
        GhibliFilmCloudDataSourceImpl(
            filmService = get(),
            trailerService = get()
        )
    }

    single<GhibliFilmRepository> { GhibliFilmRepositoryImpl(ghibliFilmCloudDataSource = get()) }

    single<GetGhibliFilmUseCase> { GetGhibliFilmUseCaseImpl(getGhibliFilmUseCaseImpl = get()) }

    viewModel { (movieId: String) ->
        GhibliFilmDetailViewModel(movieId = movieId, ghibliFilmUseCaseImpl = get())
    }
}

