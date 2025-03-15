package com.matheus.movieapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.matheus.movieapp.BuildConfig
import com.matheus.movieapp.data.local.dao.MovieDao
import com.matheus.movieapp.data.local.entity.MovieEntity
import com.matheus.movieapp.data.mapper.toDomain
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.model.Video
import com.matheus.movieapp.data.paging.MoviePagingSource
import com.matheus.movieapp.data.remote.MovieApi
import com.matheus.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    companion object {
        private const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map { entities ->
            entities.map { entity ->
                Movie(
                    id = entity.id,
                    title = entity.title,
                    overview = entity.overview,
                    posterUrl = entity.posterUrl ?: "",
                    releaseDate = entity.releaseDate,
                    posterPath = ""
                )
            }
        }
    }

    override suspend fun addMovieToFavorites(movie: MovieEntity) {
        try {
            movieDao.insertFavoriteMovie(movie)
            Log.d("MovieRepository", "Filme ${movie.title} adicionado aos favoritos.")
        } catch (e: Exception) {
            Log.e("MovieRepository", "Erro ao adicionar favorito", e)
        }
    }

    override suspend fun removeMovieFromFavorites(movie: MovieEntity) {
        try {
            movieDao.deleteFavoriteMovie(movie)
            Log.d("MovieRepository", "Filme ${movie.title} removido dos favoritos.")
        } catch (e: Exception) {
            Log.e("MovieRepository", "Erro ao remover favorito", e)
        }
    }


    override suspend fun isMovieFavorite(movieId: Int): Boolean {
        return movieDao.isMovieFavorite(movieId)
    }

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        TODO("Not yet implemented")
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api, API_KEY) }
        ).flow
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            val response = api.searchMovies(query, API_KEY)
            response.movies.map { it.toDomain() }
        }
    }

    override suspend fun getMovieTrailer(movieId: Int): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieVideos(movieId, API_KEY)
                response.videos.find { it.site == "YouTube" && it.type == "Trailer" }?.key
            } catch (e: Exception) {
                Log.e("MovieRepositoryImpl", "Erro ao buscar trailer", e)
                null
            }
        }
    }
}