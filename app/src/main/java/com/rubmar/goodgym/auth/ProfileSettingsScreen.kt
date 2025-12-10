package com.rubmar.goodgym.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileSettingsScreen(navController: NavController, authViewModel: AuthViewModel, userId: String?) {
    val deleteUserState by authViewModel.deleteUserState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }

    LaunchedEffect(deleteUserState) {
        if (deleteUserState is AuthResult.Success) {
            navController.navigate("login") { popUpTo(0) }
            authViewModel.resetDeleteUserState()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f))) {
        // Botón para Volver
        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.TopStart).padding(8.dp)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Ajustes del Perfil", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showModifyDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Modificar Perfil")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Eliminar Perfil")
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = deleteUserState) {
                is AuthResult.Loading -> CircularProgressIndicator()
                is AuthResult.Error -> Text(text = state.message ?: "Error", color = Color.Red)
                else -> {}
            }
        }
    }

    if (showDeleteDialog) {
        ConfirmationDialog(
            title = "Confirmar Eliminación",
            text = "¿Estás seguro de que quieres eliminar tu perfil? Esta acción es irreversible.",
            onConfirm = {
                if (userId != null) {
                    authViewModel.deleteUser(userId)
                }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showModifyDialog) {
        ConfirmationDialog(
            title = "Modificar Perfil",
            text = "Vas a ser redirigido para editar tus datos. ¿Quieres continuar?",
            onConfirm = {
                showModifyDialog = false
                navController.navigate("edit_profile/$userId")
            },
            onDismiss = { showModifyDialog = false }
        )
    }
}

@Composable
private fun ConfirmationDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
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