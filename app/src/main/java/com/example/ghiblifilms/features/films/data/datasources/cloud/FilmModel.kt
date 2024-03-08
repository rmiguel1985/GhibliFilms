package com.example.ghiblifilms.features.films.data.datasources.cloud

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class FilmModel(

    @JsonNames("id") var id: String = "",
    @JsonNames("title") var title: String = "",
    @JsonNames("original_title") var originalTitle: String = "",
    @JsonNames("original_title_romanised") var originalTitleRomanised: String = "",
    @JsonNames("image") var image: String = "",
    @JsonNames("movie_banner") var movieBanner: String = "",
    @JsonNames("description") var description: String = "",
    @JsonNames("url") var url: String = "",
    @JsonNames("director") var director: String = "",
    @JsonNames("producer") var producer: String = "",
    @JsonNames("release_date") var releaseDate: String = "",
    @JsonNames("running_time") var runningTime: String = "",
    @JsonNames("rt_score") var rtScore: String = "",
    @JsonNames("people") var people: ArrayList<String> = arrayListOf(),
    @JsonNames("species") var species: ArrayList<String> = arrayListOf(),
    @JsonNames("locations") var locations: ArrayList<String> = arrayListOf(),
    @JsonNames("vehicles") var vehicles: ArrayList<String> = arrayListOf(),
)
