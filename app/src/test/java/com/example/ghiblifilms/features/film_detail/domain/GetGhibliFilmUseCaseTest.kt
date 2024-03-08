package com.example.ghiblifilms.features.film_detail.domain

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Id
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Items
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.film_detail.data.repository.GhibliFilmRepository
import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetGhibliFilmUseCaseTest {
    private val ghibliFilmRepository: GhibliFilmRepository = mockk()
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


    private val filmUseCase: GetGhibliFilmUseCase = GetGhibliFilmUseCaseImpl(ghibliFilmRepository)

    @Test
    fun `on success get movie and trailer expected values are present in entity`() = runTest {
        // Given
        val expectedEntity = FilmEntity(
            title = film.title,
            description = film.description,
            youtubeVideoId = trailerId
        )
        coEvery { ghibliFilmRepository.getFilm(any()) } returns Result.success(film)
        coEvery { ghibliFilmRepository.getTrailer(any()) } returns Result.success(trailer)

        // When
        val filmEntity = filmUseCase.execute("id")

        // Then
        coVerify { ghibliFilmRepository.getFilm("id") }
        coVerify { ghibliFilmRepository.getTrailer(film.title) }
        Assertions.assertTrue(filmEntity.isSuccess)
        Assertions.assertTrue(filmEntity.getOrThrow() == expectedEntity)
    }

    @Test
    fun `on success get movie but fail on trailer videoId should be empty String`() = runTest {
        // Given
        val expectedEntity = FilmEntity(
            title = film.title,
            description = film.description,
            youtubeVideoId = ""
        )
        coEvery { ghibliFilmRepository.getFilm(any()) } returns Result.success(film)
        coEvery { ghibliFilmRepository.getTrailer(any()) } returns Result.failure(Exception("Error getting trailer"))

        // When
        val filmEntity = filmUseCase.execute("id")

        // Then
        Assertions.assertTrue(filmEntity.isSuccess)
        Assertions.assertTrue(filmEntity.getOrThrow() == expectedEntity)
    }
}

