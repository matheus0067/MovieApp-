package com.matheus.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String
)

data class MovieResponseDto(
    @SerializedName("results") val movies: List<MovieDto>
)
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = releaseDate,
        posterPath = posterPath ?: ""
    )
}
