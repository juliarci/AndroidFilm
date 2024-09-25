package com.example.firstapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass


@Composable
fun Profil(
    modifier: Modifier = Modifier,
    classes: WindowSizeClass,
    navController: NavHostController
) {
    val classWidth = classes.windowWidthSizeClass
    when (classWidth) {
        WindowWidthSizeClass.COMPACT -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                ProfileContent(navController)
            }
        }

        else -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                ProfileContentCompact(navController)
            }
        }
    }

}

@Composable
fun ProfileContent(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileImage()
        Spacer(Modifier.height(16.dp))
        ProfileText()
        Spacer(Modifier.height(50.dp))
        ContactInfo()
        Spacer(Modifier.height(32.dp))
        StartButton(navController)
    }
}

@Composable
fun ProfileContentCompact(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(16.dp))
            ProfileImage()
            Spacer(Modifier.height(16.dp))
            ProfileText()
        }
        Spacer(Modifier.width(100.dp))
        ComposeButton(navController)
    }
}

@Composable
fun ProfileImage() {
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

@Composable
fun ProfileText() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Julia Ricchi",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Étudiante en quatrième année en alternance",
        )
        Text(
            text = "École d'ingénieur ISIS - INU Champollion",
            fontStyle = FontStyle.Italic,
        )
    }
}

@Composable
fun ContactInfo() {
    Column {
        ContactItem(
            text = "julia.ricchi@orange.fr",
            icon = painterResource(id = R.drawable.email)
        )
        ContactItem(
            text = "www.linkedin.com",
            icon = painterResource(id = R.drawable.linkedin_icon_svg)
        )
    }
}

@Composable
fun ContactItem(text: String, icon: Painter) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ComposeButton(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ContactInfo()
        Spacer(Modifier.height(50.dp))
        StartButton(navController)
    }
}

@Composable
fun StartButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(Films()) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE333FF)
        )
    ) {
        Text("Démarrer")
    }
}


