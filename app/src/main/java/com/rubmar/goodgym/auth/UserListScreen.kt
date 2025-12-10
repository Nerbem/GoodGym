package com.rubmar.goodgym.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UserListScreen(navController: NavController, authViewModel: AuthViewModel) {
    val userListState by authViewModel.userListState.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.getUsers()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header con botón para volver
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }
                Text(
                    text = "Usuarios Registrados", 
                    color = Color.White, 
                    fontSize = 22.sp, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Contenido de la lista
            when (val state = userListState) {
                is AuthResult.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is AuthResult.Success -> {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(state.data) { user ->
                            UserListItem(user)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                is AuthResult.Error -> {
                    Text(text = state.message ?: "Error", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun UserListItem(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        Text(text = "${user.nombre} ${user.apellido}", fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = "Email: ${user.email}", color = Color.White.copy(alpha = 0.8f))
        Text(text = "Edad: ${user.edad}", color = Color.White.copy(alpha = 0.8f))
    }
}
