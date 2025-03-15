package com.matheus.movieapp.data.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String,
    val posterPath: String?
)