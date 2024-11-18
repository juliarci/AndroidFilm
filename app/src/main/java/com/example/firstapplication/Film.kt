package com.example.firstapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun FilmFun(viewModel: MainViewModel, id: String) {
    val filmDetail by viewModel.filmDetail.collectAsState()
    viewModel.filmDetail(id)
    filmDetail?.let {
        Text(
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = it.original_title
        )
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780/" + it.poster_path,
        contentDescription = "Image du film"
        )
    }
}