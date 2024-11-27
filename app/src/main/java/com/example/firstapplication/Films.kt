package com.example.firstapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.firstapplication.daos.FilmDao
import com.example.firstapplication.model.Movie
import com.example.firstapplication.ui.utils.ErrorMessage
import com.example.firstapplication.ui.utils.LoadingIndicator
import kotlinx.coroutines.launch

@Composable
fun FilmsFun(
    navController: NavHostController,
    viewModel: MainViewModel,
    classes: WindowSizeClass
) {
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(true) {
        viewModel.trendingFilms()
    }

    val classWidth = classes.windowWidthSizeClass

    when (classWidth) {
        WindowWidthSizeClass.COMPACT -> {
            FilmsFunCompact(navController, movies, isLoading, errorMessage, viewModel)
        }
        else -> {
            FilmsFunExpanded(navController, movies, isLoading, errorMessage, viewModel)
        }
    }
}

@Composable
fun FilmsFunCompact(
    navController: NavHostController,
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?,
    viewModel: MainViewModel
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isLoading) {
                LoadingIndicator()
            } else if (errorMessage != null) {
                ErrorMessage(errorMessage)
            } else {
                MovieGrid(navController, movies, columns = 2, viewModel)
            }
        }
    }
}

@Composable
fun FilmsFunExpanded(
    navController: NavHostController,
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?,
    viewModel: MainViewModel
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isLoading) {
                LoadingIndicator()
            } else if (errorMessage != null) {
                ErrorMessage(errorMessage)
            } else {
                MovieGridExpanded(navController, movies, columns = 4, viewModel)
            }
        }
    }
}

@Composable
fun MovieGrid(
    navController: NavHostController,
    movies: List<Movie>,
    columns: Int,
    viewModel: MainViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {
        items(movies) { movie ->
            MovieCard(navController, movie, viewModel)
        }
    }
}

@Composable
fun MovieGridExpanded(
    navController: NavHostController,
    movies: List<Movie>,
    columns: Int,
    viewModel: MainViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 100.dp, end = 20.dp)
    ) {
        items(movies) { movie ->
            MovieCard(navController, movie, viewModel)
        }
    }
}

@Composable
fun MovieCard(
    navController: NavHostController,
    movie: Movie,
    viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    ElevatedCard(
        onClick = { navController.navigate(Film(movie.id.toString())) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 200.dp, height = 350.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${movie.poster_path}",
            contentDescription = "Image du film"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.original_title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    scope.launch {
                        viewModel.toggleFavorite(movie)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = if (movie.isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (movie.isFav) "Retirer des favoris" else "Ajouter aux favoris",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Text(
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center,
            text = movie.release_date
        )
    }
}
