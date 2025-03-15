package com.matheus.movieapp.presentation.viewmodel

import androidx.paging.PagingData
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.repository.FakeMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FakeMovieViewModel : MovieViewModel(FakeMovieRepository()) {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.from(
        listOf(
            Movie(
                id = 1,
                title = "Filme Teste",
                overview = "Sinopse de teste",
                posterUrl = "poster.jpg",
                releaseDate = "2024-01-01",
                posterPath = "" // ✅ Corrigido: Agora passa o parâmetro obrigatório
            )
        )
    ))

    override val movies: StateFlow<PagingData<Movie>> = _movies
}