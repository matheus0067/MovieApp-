package com.matheus.movieapp.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.presentation.ui.components.ShimmerEffect
import com.matheus.movieapp.presentation.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Movie) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filmes Populares") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar Filmes")
                    }
                    IconButton(onClick = onFavoritesClick) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favoritos")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(padding)
        ) {
            if (favoriteMovies.isNotEmpty()) {
                item {
                    Text(
                        text = "Favoritos",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(favoriteMovies) { movie ->
                    MovieItem(movie = movie, onMovieClick = onMovieClick)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) } // Espaço entre seções
            }

            item {
                Text(
                    text = "Filmes Populares",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (movies.loadState.refresh is LoadState.Loading) {
                item { ShimmerEffect() }
            } else {
                items(movies.itemCount) { index ->
                    movies[index]?.let { movie ->
                        MovieItem(movie, onMovieClick)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieClick: (Movie) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onMovieClick(movie) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(movie.title, style = MaterialTheme.typography.titleLarge)
                Text(movie.releaseDate, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

