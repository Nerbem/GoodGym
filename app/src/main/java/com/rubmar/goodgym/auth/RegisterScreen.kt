package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf(false) }
    var apellidoError by remember { mutableStateOf(false) }
    var edadError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(125.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.header_background),
                    contentDescription = "Fondo de cabecera",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Registro", 
                    color = Color.White, 
                    fontSize = 32.sp, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(y = 30.dp)
                )
            }
            
            Column(modifier = Modifier.padding(32.dp)) {
                CustomTextField(value = nombre, onValueChange = { nombre = it; nombreError = false }, placeholder = "Nombre", isError = nombreError)
                Spacer(Modifier.height(8.dp))
                CustomTextField(value = apellido, onValueChange = { apellido = it; apellidoError = false }, placeholder = "Apellido", isError = apellidoError)
                Spacer(Modifier.height(8.dp))
                CustomTextField(value = edad, onValueChange = { edad = it; edadError = false }, placeholder = "Edad", keyboardType = KeyboardType.Number, isError = edadError)
                Spacer(Modifier.height(8.dp))
                CustomTextField(value = email, onValueChange = { email = it; emailError = false }, placeholder = "Email", isError = emailError)
                Spacer(Modifier.height(8.dp))
                CustomTextField(value = password, onValueChange = { password = it; passwordError = false }, placeholder = "Contraseña", isPassword = true, isError = passwordError)
                Spacer(Modifier.height(8.dp))
                CustomTextField(value = confirmPassword, onValueChange = { confirmPassword = it; passwordError = false }, placeholder = "Confirmar Contraseña", isPassword = true, isError = passwordError)
                
                Spacer(Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Volver") }
                    Button(
                        onClick = { 
                            val isNombreValid = nombre.isNotBlank() && nombre.all { it.isLetter() }
                            val isApellidoValid = apellido.isNotBlank() && apellido.all { it.isLetter() }
                            val isEmailValid = email.contains("@") && (email.endsWith(".com") || email.endsWith(".es"))
                            val ageInt = edad.toIntOrNull()
                            val isEdadValid = ageInt != null && ageInt in 16..99
                            val doPasswordsMatch = password.isNotBlank() && password == confirmPassword

                            nombreError = !isNombreValid
                            apellidoError = !isApellidoValid
                            emailError = !isEmailValid
                            edadError = !isEdadValid
                            passwordError = !doPasswordsMatch

                            if (isNombreValid && isApellidoValid && isEmailValid && isEdadValid && doPasswordsMatch) {
                                navController.navigate("subscription/$nombre/$apellido/$edad/$email/$password")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Continuar") }
                }
            }
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        contentAlignment = Alignment.CenterStart, 
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(if (isError) 2.dp else 0.dp, Color.Red, RoundedCornerShape(1.dp)) // Borde rojo si hay error
    ) {
        Image(painter = painterResource(id = R.drawable.textfield_background), contentDescription = null, contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())
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
