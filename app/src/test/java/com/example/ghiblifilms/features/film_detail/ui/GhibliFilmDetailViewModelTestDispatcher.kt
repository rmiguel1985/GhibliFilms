package com.example.ghiblifilms.features.film_detail.ui

import app.cash.turbine.test
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCase
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCaseImpl
import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity
import com.example.utils.TestDispatcher
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GhibliFilmDetailViewModelTestDispatcher : TestDispatcher() {

    private val useCase: GetGhibliFilmUseCase = mockk<GetGhibliFilmUseCaseImpl>()
    private lateinit var viewModel: GhibliFilmDetailViewModel

    private val expectedFilm: FilmEntity =
        FilmEntity(
            title = "Title 1",
            description = "Description 1",
        )

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `on success request UiState contains expected films list`() =
        runTest() {
            // Given
            val expectedUiState =
                GhibliFilmDetailViewModel.DetailUiState(
                    loading = false,
                    film = expectedFilm,
                    error = ""
                )
            coEvery { useCase.execute(any()) } returns Result.success(expectedFilm)

            // When
            viewModel =
                GhibliFilmDetailViewModel(
                    "",
                    useCase
                )

            // Then
            viewModel.state.test {
                assert(awaitItem() == expectedUiState)
            }
        }

    @Test
    fun `on fail request UiState contains expected error`() =
        runTest() {
            // Given
            val errorMessage = "Unexpected error"
            val expectedUiState = GhibliFilmDetailViewModel.DetailUiState(
                loading = false,
                error = errorMessage
            )
            coEvery { useCase.execute(any()) } returns Result.failure(Exception(errorMessage))

            // When
            viewModel =
                GhibliFilmDetailViewModel(
                    "",
                    useCase
                )

            // Then
            viewModel.state.test {
                assert(awaitItem().error == expectedUiState.error)
            }
        }
}
