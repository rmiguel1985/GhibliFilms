package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSource
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsCloudDataSourceImpl
import com.example.ghiblifilms.features.films.data.datasources.cloud.GhibliFilmsService
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasource
import com.example.ghiblifilms.features.films.data.datasources.disk.GhibliFilmsDiskDatasourceImpl
import com.example.ghiblifilms.features.films.data.datasources.disk.schema.AppDatabase
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsCloudWithCachePolicyImpl
import com.example.ghiblifilms.features.films.data.policy.GhibliFilmsPolicy
import com.example.ghiblifilms.utils.ConnectivityHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import retrofit2.Retrofit
import java.io.IOException
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalSerializationApi::class)
@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GhibliFilmsRoomDatasourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var context: Context? = null
    private lateinit var appDatabase: AppDatabase
    private lateinit var roomDiskDataSource: GhibliFilmsDiskDatasource
    private lateinit var cloudWithCachePolicyImpl: GhibliFilmsPolicy
    private lateinit var mockWebServer: MockWebServer
    private lateinit var filmsRetrofitDataSourceImpl: GhibliFilmsCloudDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        context = InstrumentationRegistry.getInstrumentation().context
        appDatabase = Room.inMemoryDatabaseBuilder(
            context!!,
            AppDatabase::class.java
        ).build()
        roomDiskDataSource = GhibliFilmsDiskDatasourceImpl(appDatabase.GhibliFilmsDao())
        mockkObject(ConnectivityHelper)

        filmsRetrofitDataSourceImpl =
            GhibliFilmsCloudDataSourceImpl(
                createRetrofitInstance().create(GhibliFilmsService::class.java)
            )

        cloudWithCachePolicyImpl =
            GhibliFilmsCloudWithCachePolicyImpl(
                filmsRetrofitDataSourceImpl,
                roomDiskDataSource
            )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        appDatabase.clearAllTables()
        appDatabase.close()
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
    fun getGhibliFilms_on_empty_database_returns_null() = runTest {
        //Given
        every { ConnectivityHelper.isOnline } returns false

        //When
        cloudWithCachePolicyImpl.getGhibliFilms()
        val films = appDatabase.GhibliFilmsDao().getAll()

        //Then
        assert(films.isEmpty())
    }

    @Test
    fun getGhibliFilms_with_success_data_from_cloud_saves_it() = runTest(timeout = 60.seconds) {
        //Given
        every { ConnectivityHelper.isOnline } returns true
        mockWebServer.enqueue(
            MockResponse()
                .setBody(filmsData)
        )

        //When
        cloudWithCachePolicyImpl.getGhibliFilms()

        //Then
        println()
        println(appDatabase.GhibliFilmsDao().getAll().isEmpty())
        assertFalse(appDatabase.GhibliFilmsDao().getAll().isEmpty())
    }

    @Test
    fun getGhibliFilms_with_saved_cached_data_returns_expected_values() = runTest(timeout = 20.seconds) {
        //Given
        every { ConnectivityHelper.isOnline } returns true
        mockWebServer.enqueue(
            MockResponse()
                .setBody(filmsData
                )
        )

        //When
        cloudWithCachePolicyImpl.getGhibliFilms()
        every { ConnectivityHelper.isOnline } returns false
        val films = cloudWithCachePolicyImpl.getGhibliFilms()

        //Then
        println("isSuccess ${films.isSuccess}")
        println("Error: ${films.getOrThrow()}")
        assertTrue(films.isSuccess)
        assertTrue(films.getOrNull()?.isNotEmpty()?: false)
    }
}