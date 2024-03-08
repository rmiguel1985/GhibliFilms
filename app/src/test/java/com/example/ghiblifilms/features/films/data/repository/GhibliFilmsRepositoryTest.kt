package com.example.ghiblifilms.features.films.data.repository

import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsPolicy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GhibliFilmsRepositoryTest {
    private val policy: GhibliFilmsPolicy = mockk()
    private lateinit var repository: GhibliFilmsRepository
    private val items: List<FilmModel> = (1..10).map {
        FilmModel(
            id = it.toString(),
            title = "Title $it",
            description = "Description $it",
        )
    }

    @BeforeEach
    fun setUp() {
        repository = GhibliFilmsRepositoryImpl(policy)
    }

    @Test
    fun `on success repository returns expected data`() = runTest {
        // Given
        coEvery { policy.getGhibliFilms() } returns Result.success(items)

        // When
        val films = repository.getFilms()

        // Then
        coVerify { policy.getGhibliFilms() }
        assertTrue(films.isSuccess)
        assertEquals(items, films.getOrThrow())
    }

    @Test
    fun `on fail repository returns expected data`() = runTest {
        // Given
        val exception = Exception("Error: error getting films")
        coEvery { policy.getGhibliFilms() } returns Result.failure(exception)

        // When
        val films = repository.getFilms()

        // Then
        coVerify { policy.getGhibliFilms() }
        assertTrue(films.isFailure)
        assertEquals(exception, films.exceptionOrNull())
    }
}
