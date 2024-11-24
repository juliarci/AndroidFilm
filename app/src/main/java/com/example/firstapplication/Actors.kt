package com.example.firstapplication

import Person
import TvShow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun ActorsFun(
    navController: NavHostController,
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
            ActorsFunCompact(navController, actors)
        }
        else -> {
            ActorsFunExpanded(navController, actors)
        }
    }
}

@Composable
fun ActorsFunCompact(navController: NavHostController, actors: List<Person>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ActorsGrid(navController, actors, columns = 2)
            NavigationButton(navController)
        }
    }
}

@Composable
fun ActorsFunExpanded(navController: NavHostController, actors: List<Person>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ActorsGrid(navController, actors, columns = 4)
            NavigationButton(navController)
        }
    }
}

@Composable
fun ActorsGrid(
    navController: NavHostController,
    actors: List<Person>,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp)
    ) {
        items(actors) { actor ->
            ActorCard(navController, actor)
        }
    }
}

@Composable
fun ActorCard(
    navController: NavHostController,
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
            text = actor.original_name
        )
    }
}

@Composable
fun NavigationButton(navController: NavHostController) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { navController.navigate(Profile()) },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE333FF))
    ) {
        Text("Page de garde")
    }
}
