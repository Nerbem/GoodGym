package com.rubmar.goodgym.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController, userId: String?, userName: String?) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isStaffVerified by remember { mutableStateOf(false) } 
    var showStaffPasswordDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent,
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = R.drawable.drawer_background), contentDescription = "Fondo del menú", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 48.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val currentDate = sdf.format(Date())
                                Text(text = userName ?: "Usuario", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = currentDate, fontSize = 14.sp, color = Color.White)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.Person, contentDescription = "Icono de Usuario", modifier = Modifier.size(48.dp), tint = Color.White)
                        }

                        Divider(modifier = Modifier.padding(vertical = 24.dp), color = Color.White.copy(alpha = 0.5f))

                        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                            if (isStaffVerified) {
                                TextButton(onClick = { 
                                    scope.launch { drawerState.close() }
                                    navController.navigate("user_list")
                                }) {
                                    Text("Ver Usuarios", fontSize = 18.sp, color = Color.White)
                                }
                            } else {
                                TextButton(onClick = { showStaffPasswordDialog = true }) {
                                    Text("¿Eres personal de GoodGym?", fontSize = 18.sp, color = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(onClick = { 
                                scope.launch { drawerState.close() }
                                navController.navigate("profile_settings/$userId")
                            }) {
                                Text("Ajustes", fontSize = 18.sp, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(onClick = { 
                                scope.launch { drawerState.close() }
                                navController.navigate("info")
                            }) {
                                Text("Soporte", fontSize = 18.sp, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = { scope.launch { drawerState.close() } }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text("Cerrar Menú")
                        }
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.background_image), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.header_background),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-35).dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Column(
                        modifier = Modifier.padding(top = 72.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Hola, ${userName ?: "Usuario"}!",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.user_avatar),
                            contentDescription = "Avatar de usuario",
                            modifier = Modifier.size(100.dp).offset(y = 20.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f).padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .width(296.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .clickable { navController.navigate("request_card") },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.physical_card_image),
                            contentDescription = "Pedir Tarjeta Física",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OptionImageButton(imageRes = R.drawable.btn_reservas, isSelected = selectedOption == "Reservas") { selectedOption = "Reservas" }
                        OptionImageButton(imageRes = R.drawable.btn_clases, isSelected = selectedOption == "Clases") { selectedOption = "Clases" }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OptionImageButton(imageRes = R.drawable.btn_entrenamiento_personalizado, isSelected = selectedOption == "Entrenamiento personalizado") { selectedOption = "Entrenamiento personalizado" }
                        OptionImageButton(imageRes = R.drawable.btn_objetivos, isSelected = selectedOption == "Objetivos") { selectedOption = "Objetivos" }
                    }
                }

                Button(
                    onClick = { navController.navigate("login") { popUpTo(0) } },
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                ) {
                    Text("Cerrar Sesión")
                }
            }
            
            IconButton(
                onClick = { scope.launch { drawerState.open() } },
                modifier = Modifier.align(Alignment.TopStart).padding(top = 48.dp, start = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
            }
        }
    }

    if (showStaffPasswordDialog) {
        StaffPasswordDialog(
            onDismiss = { showStaffPasswordDialog = false },
            onConfirm = { password ->
                if (password == "123GG") {
                    isStaffVerified = true
                }
                showStaffPasswordDialog = false
            }
        )
    }
}

@Composable
private fun OptionImageButton(
    @DrawableRes imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 3.dp,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
private fun StaffPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var password by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Acceso para Personal") },
        text = {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(password) }) {
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
