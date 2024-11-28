package com.example.firstapplication


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import com.example.firstapplication.model.Data
import com.example.firstapplication.model.Playlist
import com.example.firstapplication.model.Tracks

@Composable
fun MusicsFun(viewModel: MainViewModel,     classes: WindowSizeClass
) {
    val playlists by viewModel.playlist.collectAsState()

    LaunchedEffect(true) {
        viewModel.getPlayList()
    }
    val classWidth = classes.windowWidthSizeClass

    when (classWidth) {
        WindowWidthSizeClass.COMPACT -> {
            playlists?.let { MusicsFunCompact(it) }
        }
        else -> {
            playlists?.let { MusicsFunExpanded(it) }
        }
    }
}

@Composable
fun MusicsFunCompact(playlist: Playlist) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = "file:///android_asset/images/"+playlist.cover, contentDescription = "",modifier = Modifier.padding(top = 150.dp, start = 100.dp, end = 100.dp, bottom = 10.dp))
            Text(text = "Classical Essentials")
            MusicsGrid( playlist.tracks, columns = 2)
        }
    }
}

@Composable
fun MusicsFunExpanded(musics: Playlist) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(top = 150.dp, start = 100.dp, end = 100.dp, bottom = 10.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Classical Essentials")
            MusicsGridExpanded( musics.tracks, columns = 4)
        }
    }
}

@Composable
fun MusicsGrid(
    musics: Tracks,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {
        items(musics.data) { music ->
            MusicCard(music)
        }
    }
}
@Composable
fun MusicsGridExpanded(
    musics: Tracks,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(start = 100.dp, end = 20.dp)
    ) {
        items(musics.data) { music ->
            MusicCard(music)
        }
    }
}

@Composable
fun MusicCard(
    music: Data
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        AsyncImage(model = "file:///android_asset/images/"+music.album.cover, contentDescription = "")

        Text(
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            text = music.title,
            fontWeight = FontWeight.Bold,
        )
    }
}



