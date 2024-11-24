package com.example.firstapplication

import Person
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
import coil.compose.AsyncImage
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun ActorsFun(
    viewModel: MainViewModel,
    classes: WindowSizeClass
) {
    val actors by viewModel.persons.collectAsState()

    LaunchedEffect(true) {
        viewModel.trendingPersons()
    }

    val classWidth = classes.windowWidthSizeClass

    when (classWidth) {
        WindowWidthSizeClass.COMPACT -> {
            ActorsFunCompact(actors)
        }
        else -> {
            ActorsFunExpanded(actors)
        }
    }
}

@Composable
fun ActorsFunCompact(actors: List<Person>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ActorsGrid( actors, columns = 2)
        }
    }
}

@Composable
fun ActorsFunExpanded(actors: List<Person>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ActorsGrid( actors, columns = 4)
        }
    }
}

@Composable
fun ActorsGrid(
    actors: List<Person>,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {
        items(actors) { actor ->
            ActorCard(actor)
        }
    }
}

@Composable
fun ActorCard(
    actor: Person
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.size(width = 200.dp, height = 324.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/${actor.profile_path}",
            contentDescription = "Image de l'acteur"
        )
        Text(
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            text = actor.original_name,
            fontWeight = FontWeight.Bold,
            )
    }
}
