package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Id
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Items
import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import com.example.ghiblifilms.features.films.data.datasources.cloud.FilmModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit

class GhibliFilmCloudDataSourceTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var ghibliFilmCloudDataSource: GhibliFilmCloudDataSource

    private val film = FilmModel(
        title = "title",
        description = "description"
    )
    private val trailer = Trailer(
        items = listOf(
            Items(
                Id(
                    videoId = "trailerId",
                    kind = "movie"
                )
            )
        )
    )

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        ghibliFilmCloudDataSource =
            GhibliFilmCloudDataSourceImpl(
                filmService = createRetrofitInstance()
                    .create(GhibliFilmService::class.java),
                trailerService = createRetrofitInstance()
                    .create(GhibliTrailerService::class.java)
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun createRetrofitInstance(): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Test
    fun `getGhibliFilms with successful cloud response returns and saves expected json`() =
        runTest {
            // Given
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(Json.encodeToString(film))
            )

            // When
            val filmResult = ghibliFilmCloudDataSource.getFilm("id")

            // Then
            Assertions.assertTrue(filmResult.isSuccess)
            Assertions.assertEquals(filmResult.getOrNull(), film)
        }

    @Test
    fun `getGhibliFilm with cloud on successful response with malformed json`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(malformedFilmData)
        )

        // When
        val filmResult = ghibliFilmCloudDataSource.getFilm("id")

        // Then
        Assertions.assertTrue(filmResult.isFailure)
        Assertions.assertTrue(filmResult.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `getGhibliFilm with cloud on unsuccessful response with http exception`() = runTest {
        // Given
        val expectedErrorMessage = "HTTP 500 Server Error"
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("code:'500'"))

        // When
        val filmsResult = ghibliFilmCloudDataSource.getFilm("id")

        // Then
        Assertions.assertTrue(filmsResult.isFailure)
        Assertions.assertEquals(filmsResult.exceptionOrNull()?.message, expectedErrorMessage)
    }

    @Test
    @Throws(Exception::class)
    fun `getTrailer with successful cloud response returns and saves expected json`() =
        runTest {
            // Given
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(Json.encodeToString(trailer))
            )

            // When
            val trailerResult = ghibliFilmCloudDataSource.getTrailer("id")

            // Then
            Assertions.assertTrue(trailerResult.isSuccess)
            Assertions.assertEquals(trailerResult.getOrNull(), trailer)
        }

    @Test
    fun `getTrailer with cloud on unsuccessful response with http exception`() = runTest {
        // Given
        val expectedErrorMessage = "HTTP 500 Server Error"
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("code:'500'"))

        // When
        val trailerResult = ghibliFilmCloudDataSource.getTrailer("id")

        // Then
        Assertions.assertTrue(trailerResult.isFailure)
        Assertions.assertEquals(trailerResult.exceptionOrNull()?.message, expectedErrorMessage)
    }
}

