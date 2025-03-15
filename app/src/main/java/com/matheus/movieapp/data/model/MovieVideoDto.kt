package com.matheus.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieVideoDto(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String
)  {
    fun toDomain() = Video(key, site, type, "1", "Trailer Oficial")
}