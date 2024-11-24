package com.example.firstapplication

import TvShow
import androidx.compose.foundation.interaction.MutableInteractionSource
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
fun SeriesFun(
    navController: NavHostController,
    viewModel: MainViewModel,
    classes: WindowSizeClass
) {
    val series by viewModel.series.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(true) {
        viewModel.trendingSeries()
    }

    val classWidth = classes.windowWidthSizeClass

    when (classWidth) {
        WindowWidthSizeClass.COMPACT -> {
            SeriesFunCompact(navController, series, isLoading, errorMessage)
        }
        else -> {  // Mode Large ou Expanded
            SeriesFunExpanded(navController, series, isLoading, errorMessage)
        }
    }
}

@Composable
fun SeriesFunCompact(
    navController: NavHostController,
    series: List<TvShow>,
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
                SeriesGrid(navController, series, columns = 2)
            }
        }
    }
}

@Composable
fun SeriesFunExpanded(
    navController: NavHostController,
    series: List<TvShow>,
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
                SeriesGrid(navController, series, columns = 4)
            }
        }
    }
}

@Composable
fun SeriesGrid(
    navController: NavHostController,
    series: List<TvShow>,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {
        items(series) { serie ->
            SerieCard(navController, serie)
        }
    }
}

@Composable
fun SerieCard(
    navController: NavHostController,
    serie: TvShow
) {
    val interactionSource = remember { MutableInteractionSource() }

    ElevatedCard(
        onClick = { navController.navigate(Serie(serie.id.toString())) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 200.dp, height = 355.dp),
        interactionSource = interactionSource // Ajoutez ici l'argument interactionSource
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${serie.poster_path}",
            contentDescription = "Image de la série"
        )
        Text(
            modifier = Modifier.padding(6.dp),
            textAlign = TextAlign.Center,
            text = serie.original_name,
            fontWeight = FontWeight.Bold,
            )
        Text(
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center,
            text = serie.first_air_date
        )
    }
}



