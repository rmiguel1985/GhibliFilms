package com.example.ghiblifilms.features.films.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.domain.GetGhibliFilmsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                onSuccess = { filmList ->
                    _state.update { UiState(filmList = filmList) }
                },
                onFailure = { throwable: Throwable ->
                    Timber.e("Error: ${throwable.message}")
                    _state.update { UiState(error = throwable.message ?: "") }
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
