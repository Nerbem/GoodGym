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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
fun SubscriptionScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    nombre: String, 
    apellido: String, 
    edad: String, 
    email: String, 
    password: String,
    confirmPassword: String
) {
    var selectedPlan by remember { mutableStateOf<String?>(null) }
    val registrationState by authViewModel.registrationState.collectAsState()

    LaunchedEffect(registrationState) {
        if (registrationState is AuthResult.Success) {
            // Navegamos al login y limpiamos todo el historial para que no se pueda volver atrás
            navController.navigate("login") { 
                popUpTo(0) 
            }
            authViewModel.resetRegistrationState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Box(modifier = Modifier.fillMaxWidth().height(125.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.header_background), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Text(text = "Elige tu Plan", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            // Contenido central
            Column(
                modifier = Modifier.weight(1f).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Cuadrados de selección
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PlanSquare(text = "Básico", isSelected = selectedPlan == "Básico") { selectedPlan = "Básico" }
                    PlanSquare(text = "Estándar", isSelected = selectedPlan == "Estándar") { selectedPlan = "Estándar" }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PlanSquare(text = "Premium", isSelected = selectedPlan == "Premium") { selectedPlan = "Premium" }
                    PlanSquare(text = "Familiar", isSelected = selectedPlan == "Familiar") { selectedPlan = "Familiar" }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botones
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Volver") }
                    Button(
                        onClick = { 
                            authViewModel.register(email, password, confirmPassword, nombre, apellido, edad) 
                        },
                        enabled = selectedPlan != null,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Finalizar") }
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                 // Estado
                when (val state = registrationState) {
                    is AuthResult.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    is AuthResult.Error -> Text(text = state.message ?: "Error desconocido", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun PlanSquare(text: String, isSelected: Boolean, onClick: () -> Unit) {
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
