package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun EditProfileScreen(
    navController: NavController, 
    authViewModel: AuthViewModel, 
    userId: String?,
    currentNombre: String = "",
    currentApellido: String = "",
    currentEdad: String = "",
    currentEmail: String = ""
) {
    var nombre by remember { mutableStateOf(currentNombre) }
    var apellido by remember { mutableStateOf(currentApellido) }
    var edad by remember { mutableStateOf(currentEdad) }
    var email by remember { mutableStateOf(currentEmail) }
    var password by remember { mutableStateOf("") }
    var plan by remember { mutableStateOf("Básico") } 

    val updateUserState by authViewModel.updateUserState.collectAsState()

    LaunchedEffect(updateUserState) {
        if (updateUserState is AuthResult.Success) {
            navController.popBackStack()
            authViewModel.resetUpdateUserState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Editar Perfil", fontSize = 24.sp, color = Color.White)
        Spacer(Modifier.height(32.dp))

        CustomEditTextField(value = nombre, onValueChange = { nombre = it }, placeholder = "Nombre")
        Spacer(Modifier.height(8.dp))
        CustomEditTextField(value = apellido, onValueChange = { apellido = it }, placeholder = "Apellido")
        Spacer(Modifier.height(8.dp))
        CustomEditTextField(value = edad, onValueChange = { edad = it }, placeholder = "Edad", keyboardType = KeyboardType.Number)
        Spacer(Modifier.height(8.dp))
        CustomEditTextField(value = email, onValueChange = { email = it }, placeholder = "Email")
        Spacer(Modifier.height(8.dp))
        CustomEditTextField(value = password, onValueChange = { password = it }, placeholder = "Nueva Contraseña (opcional)", isPassword = true)

        Spacer(Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    if (userId != null) {
                        authViewModel.updateUser(userId, nombre, apellido, edad, email, password, plan)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
            ) {
                Text("Guardar Cambios")
            }
        }

        when (val state = updateUserState) {
            is AuthResult.Loading -> CircularProgressIndicator()
            is AuthResult.Error -> Text(text = state.message ?: "Error", color = Color.Red)
            else -> {}
        }
    }
}

@Composable
private fun CustomEditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth().height(56.dp)) {
        Image(
            painter = painterResource(id = R.drawable.textfield_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            cursorBrush = SolidColor(Color.White),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
        if (value.isEmpty()) {
            Text(text = placeholder, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}