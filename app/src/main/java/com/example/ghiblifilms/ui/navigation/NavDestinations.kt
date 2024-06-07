package com.example.ghiblifilms.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieId: String)