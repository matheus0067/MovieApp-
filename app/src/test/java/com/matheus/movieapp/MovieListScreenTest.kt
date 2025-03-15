package com.matheus.movieapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.matheus.movieapp.presentation.ui.MovieListScreen
import com.matheus.movieapp.presentation.viewmodel.FakeMovieViewModel
import org.junit.Rule
import org.junit.Test

class MovieListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun exibirListaDeFilmes() {
        composeTestRule.setContent {
            MovieListScreen(
                viewModel = FakeMovieViewModel(),
                onMovieClick = {},
                onFavoritesClick = {},
                onSearchClick = {}
            )
        }


        composeTestRule.onNodeWithText("Filmes Populares").assertExists()
        composeTestRule.onNodeWithText("Filme Teste").assertExists()
    }
}