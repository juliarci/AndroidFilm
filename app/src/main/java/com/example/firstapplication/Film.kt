package com.example.firstapplication

import Cast
import Genre
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmFun(navController: NavHostController, viewModel: MainViewModel, id: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails du Film") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Films()) }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        },
        content = {
            val filmDetail by viewModel.filmDetail.collectAsState()

            viewModel.filmDetail(id)

            filmDetail?.let { film ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp, top = 80.dp, start = 16.dp, end = 16.dp)
                ) {
                    // Components
                    item(span = { GridItemSpan(3) }) { FilmTitle(film.title) }
                    item(span = { GridItemSpan(3) }) { FilmPoster(film.poster_path) }
                    item(span = { GridItemSpan(3) }) { FilmDescription(film.overview) }
                    item(span = { GridItemSpan(3) }) { FilmGenres(film.genres) }
                    item(span = { GridItemSpan(3) }) {
                        FilmInfoRow(
                            "Sorti le:",
                            film.release_date,
                            "Durée:",
                            "${film.runtime} min"
                        )
                    }
                    item(span = { GridItemSpan(3) }) {
                        FilmInfoRow(
                            "Budget:",
                            formatCurrency(film.budget),
                            "Recettes:",
                            formatCurrency(film.revenue)
                        )
                    }
                    item(span = { GridItemSpan(3) }) {
                        FilmRating(
                            film.vote_average,
                            film.vote_count
                        )
                    }
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = "Acteurs",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(film.credits.cast) { castMember ->
                        ActorCard(castMember)
                    }
                }
            }
        }
    )
}

@Composable
fun FilmTitle(title: String) {
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
fun FilmPoster(posterPath: String) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w780/$posterPath",
        contentDescription = "Affiche du film",
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(bottom = 16.dp)
    )
}

@Composable
fun FilmDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun FilmGenres(genres: List<Genre>) {
    Text(
        text = "Genres: ${genres.joinToString { it.name }}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun FilmInfoRow(label1: String, value1: String, label2: String, value2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label1 $value1", style = MaterialTheme.typography.bodyMedium)
        Text(text = "$label2 $value2", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun FilmRating(voteAverage: Double, voteCount: Int) {
    Text(
        text = "Note: $voteAverage ($voteCount votes)",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ActorCard(castMember: Cast) {

    val profilePath = castMember.profile_path.takeIf { it.isNotEmpty() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (profilePath != null) {
            if (profilePath.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w185/$profilePath"),
                    contentDescription = "Portrait de ${castMember.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 8.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Portrait par défaut",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 8.dp)
                )
            }
        }

        Text(
            text = castMember.name ,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = castMember.character,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("DefaultLocale")
fun formatCurrency(amount: Int): String {
    return "$${String.format("%,d", amount)}"
}