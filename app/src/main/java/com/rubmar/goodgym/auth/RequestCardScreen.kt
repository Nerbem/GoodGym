package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun RequestCardScreen(navController: NavController, authViewModel: AuthViewModel) {
    var showVerificationDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .offset(y = (-20).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.physical_card_image),
                contentDescription = "Imagen de tarjeta física",
                modifier = Modifier.height(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Tener una tarjeta de socio de nuestro gimnasio es tu pase personal a un mundo de bienestar y ahorro.\n" +
                        "\n" +
                        "Con ella, no solo aseguras tu acceso a nuestras instalaciones de primera, si no que también desbloqueas descuentos exclusivos en clases grupales, entrenamientos personales e incluso en nuestra tienda de suplementos y equipamiento.\n" +
                        "\n" +
                        "Es una inversión inteligente que te motiva a mantenerte activo y te recompensa por tu compromiso con tu salud..",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                ) {
                    Text("Volver")
                }
                Button(
                    onClick = { showVerificationDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                ) {
                    Text("Aceptar")
                }
            }
        }
    }

    if (showVerificationDialog) {
        VerificationDialog(
            authViewModel = authViewModel,
            onDismiss = { 
                showVerificationDialog = false
                authViewModel.resetAuthState()
            },
            onConfirm = { 
                showVerificationDialog = false
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun VerificationDialog(
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthResult.Success) {
            onConfirm()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Verificar Identidad") },
        text = {
            Column {
                Text("Por tu seguridad, introduce tus credenciales para confirmar la petición.")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; authViewModel.resetAuthState() },
                    label = { Text("Email") },
                    isError = authState is AuthResult.Error
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; authViewModel.resetAuthState() },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = authState is AuthResult.Error
                )

                if (authState is AuthResult.Error) {
                    Text(text = (authState as AuthResult.Error).message ?: "Error desconocido", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { authViewModel.login(email, password) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
