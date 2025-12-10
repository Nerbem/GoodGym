package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun HomeScreen(navController: NavController, userName: String?) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

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
            // Header más estrecho
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.header_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Avatar de usuario en el header
                Image(
                    painter = painterResource(id = R.drawable.user_avatar),
                    contentDescription = "Avatar de usuario",
                    modifier = Modifier.size(100.dp).offset(y = 30.dp)
                )
                // Mensaje de bienvenida
                Text(
                    text = "¡Hola, ${userName ?: "Usuario"}!",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
                )
            }

            // Contenido central con las opciones
            Column(
                modifier = Modifier.weight(1f).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OptionSquare(text = "Rutinas", isSelected = selectedOption == "Rutinas") { selectedOption = "Rutinas" }
                    OptionSquare(text = "Dietas", isSelected = selectedOption == "Dietas") { selectedOption = "Dietas" }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OptionSquare(text = "Progreso", isSelected = selectedOption == "Progreso") { selectedOption = "Progreso" }
                    OptionSquare(text = "Perfil", isSelected = selectedOption == "Perfil") { selectedOption = "Perfil" }
                }
            }

            // Botón de cerrar sesión
            Button(
                onClick = { 
                    navController.navigate("login") { popUpTo(0) } 
                },
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}

@Composable
private fun OptionSquare(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.DarkGray else Color.White.copy(alpha = 0.8f))
            .border(2.dp, if (isSelected) Color.White else Color.Transparent, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = if (isSelected) Color.Black else Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}
