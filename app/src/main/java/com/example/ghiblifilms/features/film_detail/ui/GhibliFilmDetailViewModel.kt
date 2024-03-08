package com.example.ghiblifilms.features.film_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilms.features.film_detail.domain.GetGhibliFilmUseCase
import com.example.ghiblifilms.features.film_detail.domain.entities.FilmEntity
import com.example.ghiblifilms.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class GhibliFilmDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val ghibliFilmUseCaseImpl: GetGhibliFilmUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private val id = savedStateHandle.get<String>(NavArg.FilmId.key) ?: ""

    init {
        getFilm()
    }

    fun getFilm() {
        viewModelScope.launch {
            _state.value = DetailUiState(loading = true)
            ghibliFilmUseCaseImpl.execute(id).fold(
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
