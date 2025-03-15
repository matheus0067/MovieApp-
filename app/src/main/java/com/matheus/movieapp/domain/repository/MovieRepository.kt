package com.matheus.movieapp.domain.repository

import android.provider.MediaStore
import com.matheus.movieapp.data.model.Movie
import androidx.paging.PagingData
import com.matheus.movieapp.data.local.entity.MovieEntity
import com.matheus.movieapp.data.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieTrailer(movieId: Int): String?

    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun addMovieToFavorites(movie: MovieEntity)
    suspend fun removeMovieFromFavorites(movie: MovieEntity)
    suspend fun isMovieFavorite(movieId: Int): Boolean
    suspend fun getMovieVideos(movieId: Int): List<Video>
}
