package com.example.firstapplication

import Cast
import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerieFun(navController: NavHostController, viewModel: MainViewModel, id: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de la série") },
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

            serieDetail?.let {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // Trois colonnes pour les acteurs et autres éléments
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp, top = 80.dp, start = 16.dp, end = 16.dp)
                ) {
                    item {

                    }
                    // Titre du film (occupera toute la largeur)
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Affiche du film (occupera toute la largeur et bords arrondis)
                    item(span = { GridItemSpan(3) }) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w780/" + it.poster_path,
                            contentDescription = "Affiche du film",
                            modifier = Modifier
                                .fillMaxWidth() // Pour que l'image prenne toute la largeur de l'écran
                                .clip(RoundedCornerShape(16.dp)) // Coins arrondis avec un rayon de 16.dp
                                .padding(bottom = 16.dp) // Ajout d'une marge en bas
                        )
                    }

                    // Description du film (occupera toute la largeur)
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = it.overview,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Genres du film (occupant une colonne)
                    item {
                        Text(
                            text = "Genres: ${it.genres.joinToString { genre -> genre.name }}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Informations sur le film (date de sortie et durée) dans une seule ligne
                    item(span = { GridItemSpan(3) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Sorti le: ${it.first_air_date}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Nombre de saisons : ${it.number_of_seasons} min",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Budget et recettes du film dans une seule ligne
                    item(span = { GridItemSpan(3) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Nombre d'épisodes: ${formatCurrencySerie(it.number_of_episodes)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Note du film dans une seule ligne
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = "Note: ${it.vote_average} (${it.vote_count} votes)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Section "Acteurs" (occupant toute la largeur)
                    item(span = { GridItemSpan(3) }) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Acteurs",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Affichage des acteurs dans une grille de 3 colonnes
                    it.credits.cast.let { castList ->
                        items(castList) { castMember ->
                            ActorCardSerie(castMember)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ActorCardSerie(castMember: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding autour de chaque acteur
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w185/${castMember.profile_path}",
            contentDescription = "Portrait de ${castMember.name}",
            modifier = Modifier
                .fillMaxWidth() // Largeur uniforme pour tous les portraits
                .height(180.dp) // Hauteur uniforme pour tous les acteurs
                .clip(RoundedCornerShape(50.dp)) // Coins arrondis
                .padding(bottom = 8.dp)
        )

        Text(
            text = castMember.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            textAlign = TextAlign.Center
        )

        if (castMember.character.isNotEmpty()) {
            Text(
                text = castMember.character,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatCurrencySerie(amount: Int): String {
    return "$${String.format("%,d", amount)}"
}
