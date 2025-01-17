package com.example.firstapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.firstapplication.ui.theme.FirstApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
class Profile

@Serializable
class Films

@Serializable
data class Film(val id: String)

@Serializable
class Series

@Serializable
data class Serie(val id: String)

@Serializable
class Actors

@Serializable
class Musics

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            var searchText by remember { mutableStateOf("") }
            var isSearching by remember { mutableStateOf(false) }
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val windowWidthSizeClass = windowSizeClass.windowWidthSizeClass
            val navController = rememberNavController()
            val backStack by navController.currentBackStackEntryAsState()
            val currentDestination = backStack?.destination
            val favoritesOnly by viewModel.favoritesOnly.collectAsState()

            FirstApplicationTheme {
                Scaffold(
                    topBar = {
                        if (currentDestination?.hasRoute<Profile>() == false && !currentDestination.hasRoute<Film>() && !currentDestination.hasRoute<Serie>()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SearchBar(
                                    leadingIcon = {
                                        if (isSearching) {
                                            IconButton(onClick = {
                                                searchText = ""
                                                isSearching = false
                                            }) {
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "Close search"
                                                )
                                            }
                                        } else {
                                            Icon(
                                                Icons.Default.Search,
                                                contentDescription = "Search"
                                            )
                                        }
                                    },
                                    content = {
                                        Text(
                                            text = "Saisissez un mot-clé pour commencer votre recherche",
                                            modifier = Modifier.padding(8.dp),
                                            color = Color.Gray
                                        )
                                    },
                                    query = searchText,
                                    onQueryChange = { searchText = it },
                                    onSearch = {
                                        searchText = it
                                        when {
                                            currentDestination.hasRoute<Films>() -> viewModel.searchMovies(
                                                it
                                            )

                                            currentDestination.hasRoute<Series>() -> viewModel.searchSeries(
                                                it
                                            )

                                            currentDestination.hasRoute<Actors>() -> viewModel.searchPersons(
                                                it
                                            )
                                        }
                                        isSearching = false
                                    },
                                    active = isSearching,
                                    onActiveChange = { isSearching = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                )
                                if (!currentDestination.hasRoute<Actors>() && !currentDestination.hasRoute<Series>()) {
                                    IconButton(
                                        onClick = { viewModel.toggleFavoritesOnly()
                                        searchText = ""}
                                    ) {
                                        Icon(
                                            imageVector = if (favoritesOnly) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "Afficher uniquement les favoris",
                                            tint = if (favoritesOnly) Color.Red else Color.Gray,
                                            modifier = Modifier.padding(top = 25.dp)
                                        )
                                    }
                                }
                            }
                        }
                    },
                    bottomBar = {
                        when (windowWidthSizeClass) {
                            WindowWidthSizeClass.COMPACT -> {
                                if (currentDestination?.hasRoute<Profile>() == false) {
                                    NavigationBar {
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    Icons.Rounded.Star,
                                                    contentDescription = "Music Icon"
                                                )
                                            },
                                            label = { Text("Page musique") },
                                            selected = currentDestination.hasRoute<Musics>(),
                                            onClick = {
                                                navController.navigate(Musics())
                                                searchText = ""
                                            })
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    Icons.Rounded.Menu,
                                                    contentDescription = "Film Icon"
                                                )
                                            },
                                            label = { Text("Page films") },
                                            selected = currentDestination.hasRoute<Films>(),
                                            onClick = {
                                                navController.navigate(Films())
                                                searchText = ""
                                            })
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    Icons.Rounded.PlayArrow,
                                                    contentDescription = "Series Icon"
                                                )
                                            },
                                            label = { Text("Page séries") },
                                            selected = currentDestination.hasRoute<Series>(),
                                            onClick = {
                                                navController.navigate(Series())
                                                searchText = ""
                                            })
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    Icons.Rounded.Person,
                                                    contentDescription = "Actors Icon"
                                                )
                                            },
                                            label = { Text("Page acteurs") },
                                            selected = currentDestination.hasRoute<Actors>(),
                                            onClick = { navController.navigate(Actors()) })
                                    }
                                }
                            }

                            else -> {
                                if (currentDestination?.hasRoute<Profile>() == false) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        NavigationRail(modifier = Modifier.padding(top = 100.dp)) {
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        Icons.Rounded.Menu,
                                                        contentDescription = "Film Icon"
                                                    )
                                                },
                                                label = { Text("Page films") },
                                                selected = currentDestination.hasRoute<Films>(),
                                                onClick = {
                                                    navController.navigate(Films())
                                                    searchText = ""
                                                })
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        Icons.Rounded.PlayArrow,
                                                        contentDescription = "Series Icon"
                                                    )
                                                },
                                                label = { Text("Page séries") },
                                                selected = currentDestination.hasRoute<Series>(),
                                                onClick = {
                                                    navController.navigate(Series())
                                                    searchText = ""
                                                })
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        Icons.Rounded.Person,
                                                        contentDescription = "Actors Icon"
                                                    )
                                                },
                                                label = { Text("Page acteurs") },
                                                selected = currentDestination.hasRoute<Actors>(),
                                                onClick = {
                                                    navController.navigate(Actors())
                                                    searchText = ""
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = Profile()) {
                        composable<Profile> {
                            Profil(
                                modifier = Modifier.padding(innerPadding),
                                windowSizeClass, navController
                            )
                        }
                        composable<Films> {
                            FilmsFun(navController, viewModel, windowSizeClass)
                        }
                        composable<Musics> {
                            MusicsFun(viewModel, windowSizeClass)
                        }
                        composable<Actors> {
                            ActorsFun(viewModel, windowSizeClass)
                        }
                        composable<Series> {
                            SeriesFun(navController, viewModel, windowSizeClass)
                        }
                        composable<Film> { backStackEntry ->
                            val filmDetail: Film = backStackEntry.toRoute()
                            FilmFun(navController, viewModel, filmDetail.id, windowSizeClass)
                            searchText = ""
                        }
                        composable<Serie> { backStackEntry ->
                            val serieDetail: Serie = backStackEntry.toRoute()
                            SerieFun(navController, viewModel, serieDetail.id, windowSizeClass)
                            searchText = ""
                        }
                    }
                }
            }
        }
    }
}