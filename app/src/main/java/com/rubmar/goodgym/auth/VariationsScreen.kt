package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun VariationsScreen(navController: NavController) {
    val variationOptions = listOf(
        "Variacion 3",
        "Variacion 4",
        "Variacion 7"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Elige una Variación",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(variationOptions) { option ->
                    Button(
                        onClick = { /* TODO: Lógica para cada variación */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) {
                        Text(option, fontSize = 18.sp)
                    }
                }
            }

            Button(
                onClick = { 
                    // Vuelve a la pantalla Home, eliminando las de Objetivos y Variaciones del historial
                    navController.popBackStack("objectives", inclusive = true) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
            ) {
                Text("Volver a Home")
            }
        }
    }
}
