package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val state by authViewModel.authState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is AuthResult.Success) {
            val user = (state as AuthResult.Success<User>).data
            navController.navigate("home/${user.id}/${user.nombre}") { 
                popUpTo(0)
            }
            authViewModel.resetAuthState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(painter = painterResource(id = R.drawable.header_background), contentDescription = null, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(300.dp))
                    Image(painter = painterResource(id = R.drawable.user_avatar), contentDescription = null, modifier = Modifier.offset(y = (-80).dp).size(120.dp))
                }
            }

            Column(
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 32.dp).offset(y = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomLoginTextField(value = email, onValueChange = { email = it }, placeholder = "Email")
                Spacer(Modifier.height(8.dp))
                CustomLoginTextField(value = password, onValueChange = { password = it }, placeholder = "Contraseña", isPassword = true)

                Spacer(Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { authViewModel.login(email.trim(), password) }, 
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Iniciar sesión") }
                    Button(
                        onClick = { navController.navigate("register") }, 
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                    ) { Text("Registro") }
                }
                Spacer(Modifier.height(16.dp))

                when (val s = state) {
                    is AuthResult.Loading -> CircularProgressIndicator(color = Color.White)
                    is AuthResult.Error -> Text("Error: ${s.message}", color = Color.Red)
                    else -> {}
                }
            }

            Image(
                painter = painterResource(id = R.drawable.info_button),
                contentDescription = "Información",
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).size(48.dp).clickable { navController.navigate("info") }
            )
        }
    }
}

@Composable
private fun CustomLoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth().height(56.dp)) {
        Image(painter = painterResource(id = R.drawable.textfield_background), contentDescription = null, contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            cursorBrush = SolidColor(Color.White),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (value.isEmpty()) {
            Text(text = placeholder, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}