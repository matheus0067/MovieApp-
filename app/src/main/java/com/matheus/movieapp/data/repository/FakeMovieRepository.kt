package com.matheus.movieapp.data.repository

import androidx.paging.PagingData
import com.matheus.movieapp.data.local.entity.MovieEntity
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.model.Video
import com.matheus.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return flowOf(
            PagingData.from(
                listOf(
                    Movie(
                        id = 1,
                        title = "Filme Teste",
                        overview = "Sinopse de teste",
                        posterUrl = "poster.jpg",
                        releaseDate = "2024-01-01",
                        posterPath = ""
                    )
                )
            )
        )
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return flowOf(emptyList())
    }

    override suspend fun addMovieToFavorites(movie: MovieEntity) {

    }

    override suspend fun removeMovieFromFavorites(movie: MovieEntity) {

    }

    override suspend fun isMovieFavorite(movieId: Int): Boolean {
        return false
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return listOf(
            Movie(
                id = 1,
                title = "Filme Teste",
                overview = "Sinopse de teste",
                posterUrl = "poster.jpg",
                releaseDate = "2024-01-01",
                posterPath = ""
            )
        )
    }

    override suspend fun getMovieTrailer(movieId: Int): String? {
        return "https://www.youtube.com/watch?v=dQw4w9WgXcQ" // Mocka um trailer válido
    }

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        return listOf(
            Video(
                id = "1",
                key = "dQw4w9WgXcQ",
                name = "Trailer Oficial",
                site = "YouTube",
                type = "Trailer"
            )
        )
    }
}