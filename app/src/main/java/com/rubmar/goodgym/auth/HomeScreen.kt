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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent,
                windowInsets = WindowInsets(0, 0, 0, 0) // Ignoramos los márgenes del sistema
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.drawer_background),
                        contentDescription = "Fondo del menú",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
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

                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            TextButton(onClick = { 
                                scope.launch { drawerState.close() }
                                navController.navigate("user_list")
                            }) {
                                Text("Ver Usuarios", fontSize = 18.sp, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(onClick = { 
                                scope.launch { drawerState.close() }
                                navController.navigate("profile_settings/$userId")
                            }) {
                                Text("Ajustes", fontSize = 18.sp, color = Color.White)
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
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    Column(
                        modifier = Modifier.padding(top = 56.dp),
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
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f).padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray, 
                            contentColor = Color.White
                        )
                    ) {
                        Text("Iniciar Actividad", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OptionSquare(text = "Reservas", isSelected = selectedOption == "Reservas") { selectedOption = "Reservas" }
                        OptionSquare(text = "Clases", isSelected = selectedOption == "Clases") { selectedOption = "Clases" }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OptionSquare(text = "Entrenamiento\npersonalizado", isSelected = selectedOption == "Entrenamiento personalizado") { selectedOption = "Entrenamiento personalizado" }
                        OptionSquare(text = "Objetivos", isSelected = selectedOption == "Objetivos") { selectedOption = "Objetivos" }
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
}

@Composable
private fun OptionSquare(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.DarkGray else Color.DarkGray.copy(alpha = 0.8f))
            .border(2.dp, if (isSelected) Color.White else Color.Transparent, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, 
            color = Color.White, 
            fontWeight = FontWeight.Bold, 
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}
