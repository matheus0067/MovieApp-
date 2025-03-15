package com.matheus.movieapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.presentation.viewmodel.MovieViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(viewModel: MovieViewModel, onMovieClick: (Movie) -> Unit) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Buscar Filmes") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {


            TextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        delay(500)
                        viewModel.searchMovies(query)
                    }
                },
                label = { Text("Digite o nome do filme") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 Lista de Resultados
            if (searchResults.isEmpty() && query.isNotEmpty()) {
                Text(
                    text = "Nenhum resultado encontrado.",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(searchResults) { movie ->
                        MovieItem(movie = movie, onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}