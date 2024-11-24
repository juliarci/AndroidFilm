package com.example.firstapplication

import Movie
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.firstapplication.ui.utils.ErrorMessage
import com.example.firstapplication.ui.utils.LoadingIndicator

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
            FilmsFunCompact(navController, movies, isLoading, errorMessage)
        }
        else -> {
            FilmsFunExpanded(navController, movies, isLoading, errorMessage)
        }
    }
}

@Composable
fun FilmsFunCompact(
    navController: NavHostController,
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isLoading) {
                LoadingIndicator()
            } else if (errorMessage != null) {
                ErrorMessage(errorMessage)
            } else {
                MovieGrid(navController, movies, columns = 2)
            }
        }
    }
}

@Composable
fun FilmsFunExpanded(
    navController: NavHostController,
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isLoading) {
                LoadingIndicator()
            } else if (errorMessage != null) {
                ErrorMessage(errorMessage)
            } else {
                MovieGrid(navController, movies, columns = 4)
            }
        }
    }
}

@Composable
fun MovieGrid(
    navController: NavHostController,
    movies: List<Movie>,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {
        items(movies) { movie ->
            MovieCard(navController, movie)
        }
    }
}

@Composable
fun MovieCard(
    navController: NavHostController,
    movie: Movie
) {
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
        Text(
            modifier = Modifier.padding(6.dp),
            textAlign = TextAlign.Center,
            text = movie.original_title,
            fontWeight = FontWeight.Bold,
            )
        Text(
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center,
            text = movie.release_date
        )
    }
}
