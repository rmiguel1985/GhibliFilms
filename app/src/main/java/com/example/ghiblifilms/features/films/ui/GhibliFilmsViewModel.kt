package com.example.ghiblifilms.features.films.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class GhibliFilmsViewModel(private val getGhibliFilmsUseCase: GetGhibliFilmsUseCase) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        getGhibliFilms()
    }

    fun getGhibliFilms() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            getGhibliFilmsUseCase.execute().fold(
                onSuccess = {
                    _state.value = UiState(filmList = it)
                },
                onFailure = {
                    Timber.e("Error: ${it.message}")
                    _state.value = UiState(error = it.message ?: "")
                }
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val filmList: List<FilmModel> = emptyList(),
        val error: String = ""
    )
}
