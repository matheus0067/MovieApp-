package com.matheus.movieapp.data.mapper

import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.model.MovieDto

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
