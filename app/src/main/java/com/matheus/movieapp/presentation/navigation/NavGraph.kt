package com.matheus.movieapp.presentation.navigation

import MovieDetailScreen
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.matheus.movieapp.presentation.ui.FavoritesScreen
import com.matheus.movieapp.presentation.ui.MovieListScreen
import com.matheus.movieapp.presentation.ui.MovieSearchScreen
import com.matheus.movieapp.presentation.viewmodel.MovieViewModel

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
    object MovieSearch : Screen("movie_search")
    object Favorites : Screen("favorites")
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavGraph(viewModel: MovieViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.route,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {

        composable(Screen.MovieList.route) {
            MovieListScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                },
                onSearchClick = {
                    navController.navigate(Screen.MovieSearch.route)
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        composable(Screen.MovieSearch.route) {
            MovieSearchScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            )
        }

        composable(Screen.Favorites.route) { // 🔹 Nova tela de favoritos
            FavoritesScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            val movie =
                viewModel.movies.collectAsLazyPagingItems().itemSnapshotList.firstOrNull { it?.id == movieId }

            if (movie != null) {
                MovieDetailScreen(viewModel = viewModel, movie = movie)
            }
        }
    }
}