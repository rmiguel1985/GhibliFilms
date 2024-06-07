package com.example.ghiblifilms.features.film_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCase
import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class GhibliFilmDetailViewModel(
    private val movieId: String,
    private val ghibliFilmUseCaseImpl: GetGhibliFilmUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    init {
        getFilm()
    }

    fun getFilm() {
        viewModelScope.launch {
            _state.value = DetailUiState(loading = true)
            ghibliFilmUseCaseImpl.execute(movieId).fold(
                onSuccess = {
                    _state.value = DetailUiState(film = it)
                },
                onFailure = {
                    _state.value = DetailUiState(error = it.message ?: "")
                    Timber.e("Error: ${it.message}")
                }
            )
        }
    }

    data class DetailUiState(
        val loading: Boolean = false,
        val film: FilmEntity = FilmEntity(),
        val error: String = ""
    )
}
