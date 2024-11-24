package com.example.firstapplication

import Genre
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.firstapplication.ui.utils.ActorCard
import com.example.firstapplication.ui.utils.Poster

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SerieFun(navController: NavHostController, viewModel: MainViewModel, id: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de la Série") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Series()) }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        },
        content = {
            val serieDetail by viewModel.serieDetail.collectAsState()

            viewModel.serieDetail(id)

            serieDetail?.let { serie ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp, top = 80.dp, start = 16.dp, end = 16.dp)
                ) {
                    // Components
                    item(span = { GridItemSpan(3) }) { SerieTitle(serie.name) }
                    item(span = { GridItemSpan(3) }) { Poster(serie.poster_path) }
                    item(span = { GridItemSpan(3) }) { SerieDescription(serie.overview) }
                    item(span = { GridItemSpan(3) }) { SerieGenres(serie.genres) }
                    item(span = { GridItemSpan(3) }) {
                        SerieInfoRow(
                            "Sorti le:",
                            serie.first_air_date,
                            "Saisons:",
                            serie.number_of_seasons.toString()
                        )
                    }
                    item(span = { GridItemSpan(3) }) {
                        SerieInfoRow(
                            "Épisodes:",
                            serie.number_of_episodes.toString(),
                            "Note:",
                            "${serie.vote_average} (${serie.vote_count} votes)",
                        )
                    }
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = "Acteurs",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(serie.credits.cast) { castMember ->
                        ActorCard(castMember)
                    }
                }
            }
        }
    )
}

@Composable
fun SerieTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SerieDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 16.dp, top = 6.dp),
        textAlign = TextAlign.Justify
    )
}

@Composable
fun SerieGenres(genres: List<Genre>) {
    Text(
        text = "Genres: ${genres.joinToString { it.name }}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SerieInfoRow(label1: String, value1: String, label2: String, value2: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label1 $value1", style = MaterialTheme.typography.bodyMedium)
        Text(text = "$label2 $value2", style = MaterialTheme.typography.bodyMedium)
    }
}
