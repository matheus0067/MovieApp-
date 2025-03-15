import androidx.media3.common.MediaItem
import android.net.Uri
import android.webkit.WebView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.presentation.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(viewModel: MovieViewModel, movie: Movie) {

    val trailerUrl by viewModel.trailerUrl.collectAsStateWithLifecycle(initialValue = null)
    val isFavorite by viewModel.isMovieFavorite(movie.id).collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(movie.id) {
        viewModel.getTrailerUrl(movie.id)
    }

    val isWideScreen = LocalConfiguration.current.screenWidthDp > 600

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isWideScreen) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    MoviePoster(movie)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    MovieInfo(viewModel, movie, isFavorite)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (!trailerUrl.isNullOrBlank()) {
                        ExoPlayerSection(trailerUrl) // ExoPlayerSection
                    } else {
                        TrailerSection(trailerUrl)
                    }
                }
            }
        } else {
            Column {
                MoviePoster(movie)
                Spacer(modifier = Modifier.height(16.dp))
                MovieInfo(viewModel, movie, isFavorite)
                Spacer(modifier = Modifier.height(16.dp))
                if (!trailerUrl.isNullOrBlank()) {
                    ExoPlayerSection(trailerUrl)
                } else {
                    TrailerSection(trailerUrl)
                }
            }
        }
    }
}


@Composable
fun MoviePoster(movie: Movie) {
    AsyncImage(
        model = movie.posterUrl,
        contentDescription = "Poster do Filme",
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieInfo(viewModel: MovieViewModel, movie: Movie, isFavorite: Boolean) {
    var favoriteState by remember { mutableStateOf(isFavorite) }

    val scale by animateFloatAsState(
        targetValue = if (favoriteState) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(
                onClick = {
                    favoriteState = !favoriteState
                    if (favoriteState) {
                        viewModel.addMovieToFavorites(movie)
                    } else {
                        viewModel.removeMovieFromFavorites(movie)
                    }
                },
                modifier = Modifier.scale(scale)
            ) {
                Icon(
                    imageVector = if (favoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = if (favoriteState) Color.Red else Color.Gray,
                    modifier = Modifier.size(36.dp) // Mantém um tamanho fixo para melhor efeito
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun TrailerSection(trailerUrl: String?) {
    if (!trailerUrl.isNullOrBlank()) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadUrl(trailerUrl)
                }
            }
        )
    } else {
        Text(
            text = "Trailer não disponível",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ExoPlayerSection(videoUrl: String?) {
    if (!videoUrl.isNullOrBlank()) {
        val context = LocalContext.current
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build()
        }

        DisposableEffect(context) {
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoUrl))
                .build()

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            onDispose {
                exoPlayer.release()
            }
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxWidth().height(250.dp)
        )
    } else {
        Text(
            text = "Vídeo não disponível",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}