package com.example.ghiblifilms.features.films.di

import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSource
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSourceImpl
import com.example.ghiblifilms.features.films.data.repository.GhibliFilmsRepository
import com.example.ghiblifilms.features.films.data.repository.GhibliFilmsRepositoryImpl
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsService
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasource
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasourceImpl
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsPolicy
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsCloudWithCachePolicyImpl
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCase
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCaseImpl
import com.example.ghiblifilms.features.films.ui.GhibliFilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val FilmstModule = module {
    fun provideService(retrofit: Retrofit): GhibliFilmsService =
        retrofit.create(GhibliFilmsService::class.java)

    single<GhibliFilmsService> {provideService(retrofit = get()) }

    single<GhibliFilmsCloudDataSource> { GhibliFilmsCloudDataSourceImpl(service = get()) }

    single<GhibliFilmsDiskDatasource> { GhibliFilmsDiskDatasourceImpl(ghibliFilmsRoomDao = get()) }

    single<GhibliFilmsPolicy> {
        GhibliFilmsCloudWithCachePolicyImpl(
            filmsCloudDataSource = get(),
            filmsListDiskDatasource = get()
        )
    }

    single<GetGhibliFilmsUseCase> { GetGhibliFilmsUseCaseImpl(ghibliFilmsRepository = get()) }

    single<GhibliFilmsRepository> { GhibliFilmsRepositoryImpl(ghibliFilmsPolicy = get()) }

    viewModel { GhibliFilmsViewModel(getGhibliFilmsUseCase = get()) }
}
