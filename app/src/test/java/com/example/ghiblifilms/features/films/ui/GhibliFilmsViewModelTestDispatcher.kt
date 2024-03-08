package com.example.ghiblifilms.features.films.ui

import app.cash.turbine.test
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.data.repository.GhibliFilmsRepository
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCase
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCaseImpl
import com.example.utils.TestDispatcher
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GhibliFilmsViewModelTestDispatcher : TestDispatcher() {

    private val repository: GhibliFilmsRepository = mockk()
    private val useCase: GetGhibliFilmsUseCase = GetGhibliFilmsUseCaseImpl(repository)
    private lateinit var viewModel: GhibliFilmsViewModel

    private val expectedFilms: List<FilmModel> = (1..5).map {
        FilmModel(
            id = it.toString(),
            title = "Title $it",
            description = "Description $it",
        )
    }

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
                GhibliFilmsViewModel.UiState(loading = false, filmList = expectedFilms, error = "")
            coEvery { repository.getFilms() } returns Result.success(expectedFilms)

            // When
            viewModel =
                GhibliFilmsViewModel(
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
            val expectedUiState = GhibliFilmsViewModel.UiState(
                loading = false,
                filmList = emptyList(),
                error = errorMessage
            )
            coEvery { repository.getFilms() } returns Result.failure(Exception(errorMessage))

            // When
            viewModel =
                GhibliFilmsViewModel(
                    useCase
                )

            // Then
            viewModel.state.test {
                assert(awaitItem().error == expectedUiState.error)
            }
        }
}
