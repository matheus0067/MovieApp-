package com.matheus.movieapp.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.presentation.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: MovieViewModel = viewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favoritos") })
        }
    ) { paddingValues ->
        if (favoriteMovies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum filme favorito ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(favoriteMovies) { movie ->
                    FavoriteMovieItem(movie = movie, onMovieClick = onMovieClick, viewModel)
                }
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    viewModel: MovieViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onMovieClick(movie) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.removeMovieFromFavorites(movie) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Remover")
            }
        }
    }
}
