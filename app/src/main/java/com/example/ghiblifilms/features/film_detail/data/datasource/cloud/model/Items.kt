package com.example.ghiblifilms.features.film_detail.data.datasource.cloud.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Items(
    @JsonNames("id") var id: Id = Id(),
)
