package com.example.firstapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage

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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 110.dp)
            ) {
                item(span = { GridItemSpan(3) }) { Text(text = "Page musique") }
                item(span = { GridItemSpan(3) }) {
                    AsyncImage(model = "file:///android_assets/images/2.jpg", contentDescription = "")
                }
                item(span = { GridItemSpan(3) }) { PlayslistImage() }
            }
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(top = 120.dp, start = 100.dp, end = 20.dp)
            ) {
                item(span = { GridItemSpan(3) }) { Text(text = "Page musique") }
                item(span = { GridItemSpan(3) }) {
                    AsyncImage(model = "file:///android_assets/images/2.jpg", contentDescription = "")
                }
                item(span = { GridItemSpan(3) }) { PlayslistImage() }
            }
        }
    }
}
@Composable
fun PlayslistImage() {
    val context = LocalContext.current
    val resources = context.resources
    val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.img_2400)

    val width = originalBitmap.width
    val height = originalBitmap.height
    val size = minOf(width, height)
    val xOffset = (width - size) / 2
    val yOffset = (height - size) / 2 - 450
    val squareBitmap = Bitmap.createBitmap(originalBitmap, xOffset, yOffset, size, size)

    Image(
        bitmap = squareBitmap.asImageBitmap(),
        contentDescription = "Profil",
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
}
