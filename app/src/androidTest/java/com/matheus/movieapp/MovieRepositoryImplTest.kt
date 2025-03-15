package com.matheus.movieapp

import androidx.paging.PagingData
import com.matheus.movieapp.data.local.dao.MovieDao
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.model.MovieDto
import com.matheus.movieapp.data.model.MovieResponseDto
import com.matheus.movieapp.data.remote.MovieApi
import com.matheus.movieapp.data.repository.MovieRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private lateinit var repository: MovieRepositoryImpl

    @MockK
    private lateinit var api: MovieApi

    @MockK
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        MockKAnnotations.init(this) // ✅ Inicializa os mocks
        repository = MovieRepositoryImpl(api, movieDao)
    }

    @Test
    fun getPopularMovies_retornaListaDeFilmes() = runTest {
        val moviesDto = listOf(
            MovieDto(1, "Filme Teste", "Sinopse", "poster.jpg", "2024-01-01")
        )
        val response = MovieResponseDto(moviesDto)

        coEvery { api.getPopularMovies(any(), any()) } returns response
        coEvery { movieDao.getFavoriteMovies() } returns flowOf(emptyList())

        val result = repository.getPopularMovies().first()

        assert(result is PagingData<Movie>)
    }
}