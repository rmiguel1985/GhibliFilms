package com.example.ghiblifilms.features.film_detail.data.repository

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.GhibliFilmCloudDataSource
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Id
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Items
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GhibliFilmRepositoryTest {
    private val ghibliFilmCloudDataSource: GhibliFilmCloudDataSource = mockk()
    private val film = FilmModel(
        title = "title",
        description = "description"
    )
    private val trailerId = "test_id"

    private val trailer = Trailer(
        items = listOf(
            Items(
                Id(
                    videoId = trailerId,
                    kind = "movie"
                )
            )
        )
    )


    private val repository: GhibliFilmRepository = GhibliFilmRepositoryImpl(ghibliFilmCloudDataSource)

    @Test
    fun `on success get movie expected values are present in model`() = runTest {
        // Given
        val filmId = "filmId"
        coEvery { ghibliFilmCloudDataSource.getFilm(any()) } returns Result.success(film)

        // When
        val filmEntity = repository.getFilm(filmId)

        // Then
        coVerify { ghibliFilmCloudDataSource.getFilm(filmId) }
        Assertions.assertTrue(filmEntity.isSuccess)
        Assertions.assertTrue(filmEntity.getOrThrow() == film)
    }

    @Test
    fun `on success get trailer expected values are present in model`() = runTest {
        // Given
        val trailerId = "trailerId"
        coEvery { ghibliFilmCloudDataSource.getTrailer(any()) } returns Result.success(trailer)

        // When
        val trailerResult = repository.getTrailer(trailerId)

        // Then
        coVerify { ghibliFilmCloudDataSource.getTrailer(trailerId) }
        Assertions.assertTrue(trailerResult.isSuccess)
        Assertions.assertTrue(trailerResult.getOrThrow() == trailer)
    }

    @Test
    fun `on fail get movie returns expected exception`() = runTest {
        // Given
        val filmId = "filmId"
        val expectedException = Exception("Error: no movies found")
        coEvery { ghibliFilmCloudDataSource.getFilm(any()) } returns Result.failure(expectedException)

        // When
        val filmResult = repository.getFilm(filmId)

        // Then
        coVerify { ghibliFilmCloudDataSource.getFilm(filmId) }
        Assertions.assertTrue(filmResult.isFailure)
        Assertions.assertEquals(filmResult.exceptionOrNull(), expectedException)
    }

    @Test
    fun `on fail get trailer returns expected exception`() = runTest {
        // Given
        val trailerId = "trailerId"
        val expectedException = Exception("Error: no trailer found")
        coEvery { ghibliFilmCloudDataSource.getTrailer(any()) } returns Result.failure(expectedException)

        // When
        val trailerResult = repository.getTrailer(trailerId)

        // Then
        coVerify { ghibliFilmCloudDataSource.getTrailer(trailerId) }
        Assertions.assertTrue(trailerResult.isFailure)
        Assertions.assertEquals(trailerResult.exceptionOrNull(), expectedException)
    }
}
