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
class Profil

@Serializable
class Films

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
                        if (currentDestination?.hasRoute<Profil>() == false) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Rounded.AccountCircle,
                                            contentDescription = "Menu Icon"
                                        )
                                    }, label = { Text("Mon profil") },
                                    selected = currentDestination.hasRoute<Profil>(),
                                    onClick = { navController.navigate(Profil()) })
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Rounded.Menu,
                                            contentDescription = "Film Icon"
                                        )
                                    }, label = { Text("Page films") },
                                    selected = currentDestination.hasRoute<Films>(),
                                    onClick = { navController.navigate(Films()) })
                            }
                        }
                    }) { innerPadding ->
                    NavHost(navController = navController, startDestination = Profil()) {


                        composable<Profil> {
                            Profil(
                                modifier = Modifier.padding(innerPadding),
                                windowSizeClass, navController
                            )
                        }
                        composable<Films> {
                            FilmsFun(navController, viewModel, "Hobbit")
                        }
                    }
                }
            }
        }
    }

}