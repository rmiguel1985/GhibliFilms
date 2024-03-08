package com.example.ghiblifilms.features.film_detail.ui

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ghiblifilms.features.film_detail.domain.entities.transformToEntity
import com.example.ghiblifilms.features.film_detail.ui.Content
import com.example.ghiblifilms.features.film_detail.ui.GhibliFilmDetailViewModel
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import org.junit.Rule
import org.junit.Test

class GhibliFilmDetailTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val ctx = InstrumentationRegistry.getInstrumentation().targetContext

    private val item: FilmModel =
        FilmModel(
            id = "1",
            title = "Title 1",
            originalTitle = "original title",
            originalTitleRomanised = "original title romanised",
            description = "Description 1",
        )

    @Test
    fun detailScreenShowExpectedFilm(): Unit = with(composeTestRule) {
        this.setContent {
            val filmState =
                GhibliFilmDetailViewModel.DetailUiState(film = item.transformToEntity(""))
            Content(state = filmState)
        }

        onNodeWithText("${item.originalTitle} -  ${item.originalTitleRomanised}").isDisplayed()
        onNodeWithText(item.description).isDisplayed()
    }
}