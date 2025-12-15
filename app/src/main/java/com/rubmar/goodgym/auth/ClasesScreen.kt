package com.rubmar.goodgym.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

data class ClassInfo(@DrawableRes val imageRes: Int, val name: String)

@Composable
fun ClasesScreen(navController: NavController) {
    val classTypes = listOf(
        ClassInfo(R.drawable.class_spinning, "Spinning"),
        ClassInfo(R.drawable.class_running, "Running"),
        ClassInfo(R.drawable.class_yoga, "Yoga"),
        ClassInfo(R.drawable.class_pilates, "Pilates"),
        ClassInfo(R.drawable.class_crossfit, "Crossfit"),
        ClassInfo(R.drawable.class_dance, "Dance")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tipos de Clases",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(classTypes) { classInfo ->
                    ClassImageButton(classInfo = classInfo) {
                        navController.navigate("class_schedule/${classInfo.name}")
                    }
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
            ) {
                Text("Volver")
            }
        }
    }
}

@Composable
private fun ClassImageButton(classInfo: ClassInfo, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = classInfo.imageRes),
            contentDescription = classInfo.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.5f)).padding(vertical = 4.dp)) {
            Text(
                text = classInfo.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
