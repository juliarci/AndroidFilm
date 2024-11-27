package com.example.firstapplication

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
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.firstapplication.model.Genre
import com.example.firstapplication.ui.utils.ActorCard
import com.example.firstapplication.ui.utils.Poster

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmFun(
    navController: NavHostController,
    viewModel: MainViewModel,
    id: String,
    classes: WindowSizeClass
) {
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
                if (classes.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 100.dp, top = 80.dp, start = 16.dp, end = 16.dp)
                    ) {
                        item(span = { GridItemSpan(3) }) {
                            FilmTitle(film.title)
                        }

                        item(span = { GridItemSpan(3) }) {
                            film.poster_path?.let { it1 -> Poster(it1) }
                        }

                        item(span = { GridItemSpan(3) }) {
                            FilmDescription(film.overview)
                        }

                        item(span = { GridItemSpan(3) }) {
                            FilmGenres(film.genres)
                        }

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
                            FilmRating(film.vote_average, film.vote_count)
                        }

                        item(span = { GridItemSpan(3) }) {
                            Text(
                                text = "Acteurs",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        items(film.credits.cast) { castMember ->
                            ActorCard(castMember)
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 80.dp, start = 100.dp, end = 16.dp)
                    ) {
                        item(span = { GridItemSpan(4) }) {
                            FilmTitle(film.title)
                        }

                        item(span = { GridItemSpan(1) }) {
                            film.poster_path?.let { it1 -> Poster(it1, isLandscape = true) }
                        }
                        item(span = { GridItemSpan(3) }) {
                            Column(modifier = Modifier.padding(start = 26.dp, top = 16.dp, end = 16.dp)) {
                                FilmDescription(film.overview)

                                Spacer(modifier = Modifier.height(8.dp))
                                FilmGenres(film.genres)

                                Spacer(modifier = Modifier.height(8.dp))
                                FilmInfoRow(
                                    "Sorti le:",
                                    film.release_date,
                                    "Durée:",
                                    "${film.runtime} min"
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                FilmInfoRow(
                                    "Budget:",
                                    formatCurrency(film.budget),
                                    "Recettes:",
                                    formatCurrency(film.revenue)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                FilmRating(film.vote_average, film.vote_count)
                            }
                        }

                        item(span = { GridItemSpan(4) }) {
                            Text(
                                text = "Acteurs",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        items(film.credits.cast) { castMember ->
                            ActorCard(castMember)
                        }
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
fun FilmDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 16.dp, top = 20.dp),
        textAlign = TextAlign.Justify
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
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@SuppressLint("DefaultLocale")
fun formatCurrency(amount: Int): String {
    return "$${String.format("%,d", amount)}"
}