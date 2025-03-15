package com.matheus.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.matheus.movieapp.data.local.entity.MovieEntity
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    open val movies: StateFlow<PagingData<Movie>> = _movies

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    private val _trailerUrl = MutableStateFlow<String?>(null)
    val trailerUrl: StateFlow<String?> = _trailerUrl

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    init {
        fetchPopularMovies()
        fetchFavoriteMovies()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val results = repository.searchMovies(query)
                _searchResults.value = results
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erro ao buscar filmes", e)
            }
        }
    }

    fun addMovieToFavorites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addMovieToFavorites(
                    MovieEntity(movie.id, movie.title, movie.overview, movie.posterUrl ?: "", movie.releaseDate)
                )
                fetchFavoriteMovies() // Atualiza a lista de favoritos
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erro ao adicionar favorito", e)
            }
        }
    }

    fun removeMovieFromFavorites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.removeMovieFromFavorites(
                    MovieEntity(movie.id, movie.title, movie.overview, movie.posterUrl ?: "", movie.releaseDate)
                )
                fetchFavoriteMovies() // Atualiza a lista de favoritos
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erro ao remover favorito", e)
            }
        }
    }

    fun isMovieFavorite(movieId: Int): Flow<Boolean> = flow {
        emit(repository.isMovieFavorite(movieId))
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _movies.value = pagingData
                }
        }
    }

    private fun fetchFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies()
                .collect { favorites ->
                    _favoriteMovies.value = favorites
                }
        }
    }


    fun getTrailerUrl(movieId: Int) {
        viewModelScope.launch {
            try {
                val videoId = repository.getMovieTrailer(movieId)
                _trailerUrl.value = if (!videoId.isNullOrBlank()) "https://www.youtube.com/watch?v=$videoId" else null
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erro ao buscar trailer", e)
                _trailerUrl.value = null
            }
        }
    }
}
