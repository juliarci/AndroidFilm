package com.example.firstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstapplication.ui.theme.FirstApplicationTheme
import kotlinx.serialization.Serializable

@Serializable
class Profile

@Serializable
class Films

@Serializable
class Series

@Serializable
class Actors

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            val backStack by navController.currentBackStackEntryAsState()
            val currentDestination = backStack?.destination
            FirstApplicationTheme {
                Scaffold(
                    bottomBar = {
                        if (currentDestination?.hasRoute<Profile>() == false) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Rounded.Menu,
                                            contentDescription = "Film Icon"
                                        )
                                    }, label = { Text("Page films") },
                                    selected = currentDestination.hasRoute<Films>(),
                                    onClick = { navController.navigate(Films()) })
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Rounded.PlayArrow,
                                            contentDescription = "Series Icon"
                                        )
                                    }, label = { Text("Page séries") },
                                    selected = currentDestination.hasRoute<Series>(),
                                    onClick = { navController.navigate(Series()) })
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Rounded.Person,
                                            contentDescription = "Actors Icon"
                                        )
                                    }, label = { Text("Page acteurs") },
                                    selected = currentDestination.hasRoute<Actors>(),
                                    onClick = { navController.navigate(Actors()) })
                            }
                        }
                    }) { innerPadding ->
                    NavHost(navController = navController, startDestination = Profile()) {

                        composable<Profile> {
                            Profil(
                                modifier = Modifier.padding(innerPadding),
                                windowSizeClass, navController
                            )
                        }
                        composable<Films> {
                            FilmsFun(navController, viewModel, "Hobbit")
                        }
                        composable<Actors> {
                            ActorsFun()
                        }
                        composable<Series> {
                            SeriesFun(navController, viewModel, "Is")
                        }
                    }
                }
            }
        }
    }

}