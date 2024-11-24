package com.example.firstapplication.ui.utils

import Cast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.firstapplication.R

@Composable
fun RoundedImage(
    imageUrl: String,
    cornerRadius: Int = 16,
    aspectRatio: Float = 16f / 9f,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .clip(RoundedCornerShape(cornerRadius.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}

@Composable
fun ActorCard(castMember: Cast, cornerRadius: Int = 8) {
    val profilePath = castMember.profile_path

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        if (!profilePath.isNullOrEmpty()) {
            RoundedImage(
                imageUrl = "https://image.tmdb.org/t/p/w185/$profilePath",
                cornerRadius = cornerRadius,
                aspectRatio = 2f / 3f
            )
        } else {
            Image(
                painter = painterResource(R.drawable.profile_default),
                contentDescription = "Portrait par défaut",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(cornerRadius.dp))
                    .padding(bottom = 8.dp)
            )
        }
        Text(
            text = castMember.name,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = castMember.character,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun Poster(posterPath: String) {
    RoundedImage(
        imageUrl = "https://image.tmdb.org/t/p/w780/$posterPath",
        cornerRadius = 16,
        aspectRatio = 3f / 4f,
        modifier = Modifier
            .width(300.dp)
            .height(450.dp)
            .padding(10.dp)
    )
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        color = Color(0xFF6D7387),
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun ErrorMessage(errorMessage: String) {
    Text(
        text = errorMessage,
        color = Color.Red,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp)
    )
}