package com.example.ghiblifilms.features.film_detail.data.datasource.cloud

import com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model.Trailer
import retrofit2.http.GET
import retrofit2.http.Query

interface GhibliTrailerService {
    @GET("search?")
    suspend fun getTrailerUrl(
        @Query("part") part: String = "snippet",
        @Query("q") filmTitle: String,
        @Query("key") youtubeApiKey: String,
    ): Trailer
}
