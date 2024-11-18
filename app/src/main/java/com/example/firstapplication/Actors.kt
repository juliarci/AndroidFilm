package com.example.firstapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun ActorsFun(navController: NavHostController, viewModel: MainViewModel){
    val actors by viewModel.persons.collectAsState()

    LaunchedEffect(true) {
        viewModel.trendingPersons()
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(actors) { actor ->
                    Column {
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),modifier = Modifier
                                .size(width = 200.dp, height = 424.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w780/" + actor.profile_path,
                                contentDescription = "Image de la série"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                text = actor.original_name
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(Profile()) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE333FF)
                )
            ) {
                Text("Page de garde")
            }
        }
    }
}