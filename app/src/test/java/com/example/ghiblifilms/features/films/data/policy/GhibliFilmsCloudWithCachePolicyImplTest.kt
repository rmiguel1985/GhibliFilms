package com.example.ghiblifilms.features.films.data.policy

import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSourceImpl
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasourceImpl
import com.example.ghiblifilms.utils.ConnectivityHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GhibliFilmsCloudWithCachePolicyImplTest {

    private val ghibliFilmsDiskDatasourceImpl = mockk<GhibliFilmsDiskDatasourceImpl>()

    private val ghibliFilmsCloudDataSourceImpl = mockk<GhibliFilmsCloudDataSourceImpl>()

    private lateinit var ghibliFilmsCloudWithCachePolicyImpl: GhibliFilmsCloudWithCachePolicyImpl

    @ExperimentalCoroutinesApi
    @BeforeEach
    fun beforeEach() {
        mockkObject(ConnectivityHelper)
        ghibliFilmsCloudWithCachePolicyImpl = GhibliFilmsCloudWithCachePolicyImpl(
            ghibliFilmsCloudDataSourceImpl, ghibliFilmsDiskDatasourceImpl
        )
    }

    @Test
    fun `calling getGhibliFilms() without connectivity calls disk data source`() = runTest {
        // Given
        coEvery { ghibliFilmsDiskDatasourceImpl.getGhibliFilmsList() } returns Result.success(
            emptyList()
        )
        every { ConnectivityHelper.isOnline } returns false

        // When
        ghibliFilmsCloudWithCachePolicyImpl.getGhibliFilms()

        // Then
        coVerify { ghibliFilmsCloudWithCachePolicyImpl.getGhibliFilms() }
    }

    @Test
    fun `calling getGhibliFilms() with connectivity and success request calls cloud and disk data source`() = runTest {
        // Given
        coEvery { ghibliFilmsCloudDataSourceImpl.getGhibliFilms() } returns Result.success(
            emptyList()
        )
        coEvery { ghibliFilmsDiskDatasourceImpl.setGhibliFilmsList(any()) } just runs

        every { ConnectivityHelper.isOnline } returns true

        // When
        ghibliFilmsCloudWithCachePolicyImpl.getGhibliFilms()

        // Then
        coVerify { ghibliFilmsCloudDataSourceImpl.getGhibliFilms() }
        coVerify { ghibliFilmsDiskDatasourceImpl.setGhibliFilmsList(any()) }
    }

    @Test
    fun `calling getGhibliFilms() with connectivity and failure request calls only cloud data source`() = runTest {
        // Given
        coEvery { ghibliFilmsCloudDataSourceImpl.getGhibliFilms() } returns Result.failure(
            Exception("Error getting films")
        )
        every { ConnectivityHelper.isOnline } returns true

        // When
        ghibliFilmsCloudWithCachePolicyImpl.getGhibliFilms()

        // Then
        coVerify { ghibliFilmsCloudDataSourceImpl.getGhibliFilms() }
    }
}
