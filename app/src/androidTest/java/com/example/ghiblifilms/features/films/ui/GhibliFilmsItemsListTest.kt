package com.example.ghiblifilms.features.films.ui

import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.example.ghiblifilms.features.films.ui.view.Content
import org.junit.Rule
import org.junit.Test

class GhibliFilmsItemsListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val ctx = InstrumentationRegistry.getInstrumentation().targetContext

    private val items: List<FilmModel> = (1..100).map {
        FilmModel(
            id = it.toString(),
            title = "Title $it",
            description = "Description $it",
        )
    }

    @Test
    fun navigatesToFirstIndexAndCheckSecondElement(): Unit = with(composeTestRule) {
        this.setContent {
            Content(
                loading = false,
                filmsList = items,
                error = "",
                onMediaClick = {},
                onTryAgainClick = {})
        }
        onNode(hasScrollToIndexAction()).performScrollToIndex(0)
        onNodeWithContentDescription("Title 2").assertExists()
    }

    @Test
    fun navigateToDetailScreen(): Unit = with(composeTestRule) {
        this.setContent {
            Content(
                loading = false,
                filmsList = items,
                error = "",
                onMediaClick = {},
                onTryAgainClick = {})
        }
        onNode(hasScrollToIndexAction()).performScrollToIndex(0)
        onNodeWithContentDescription("Title 2").performClick()
        onNodeWithText("Description 2").isDisplayed()
    }
}