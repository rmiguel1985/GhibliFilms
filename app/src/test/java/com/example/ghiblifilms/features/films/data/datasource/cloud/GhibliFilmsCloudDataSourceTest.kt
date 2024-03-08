package com.example.ghiblifilms.features.films.data.datasource.cloud

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSource
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSourceImpl
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.io.IOException
import java.io.InputStream

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
class GhibliFilmsCloudDataSourceTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var ghibliFilmsCloudDataSourceImpl: GhibliFilmsCloudDataSource

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        ghibliFilmsCloudDataSourceImpl =
            GhibliFilmsCloudDataSourceImpl(
                createRetrofitInstance()
                    .create(GhibliFilmsService::class.java)
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

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
    @Throws(Exception::class)
    fun `getGhibliFilms with successful cloud response returns and saves expected json`() =
        runTest {
            // Given
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(filmsData)
            )

            // When
            val filmsResult = ghibliFilmsCloudDataSourceImpl.getGhibliFilms()

            // Then
            assertTrue(filmsResult.isSuccess)
            assertTrue(filmsResult.getOrNull()?.isNotEmpty()?: false)
        }

    @Test
    fun `getGhibliFilms with cloud on successful response with malformed json`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(malformedFilmsData)
        )

        // When
        val filmsResult = ghibliFilmsCloudDataSourceImpl.getGhibliFilms()

        // Then
        assertTrue(filmsResult.isFailure)
        assertTrue(filmsResult.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `getGhibliFilms with cloud on unsuccessful response with http exception`() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("code:'500'"))

        // When
        val filmsResult = ghibliFilmsCloudDataSourceImpl.getGhibliFilms()

        // Then
        assertTrue(filmsResult.isFailure)
        assertEquals(filmsResult.exceptionOrNull()?.message, "HTTP 500 Server Error")
    }
}
