package com.rubmar.goodgym.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@Composable
fun InfoScreen(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)) {
            append("¿Problemas para iniciar sesión? ¡Prueba esto!\n\n")
        }
        append("Revisa tus datos: Asegúrate de que tu usuario/correo y contraseña estén bien escritos (¡cuidado con mayúsculas/minúsculas!). Si no estás seguro, usa \"¿Olvidaste tu contraseña?\".\n\n")
        append("Conexión a internet: Verifica que tengas buena señal Wi-Fi o datos móviles.\n\n")
        append("Actualiza la app: Asegúrate de tener la última versión instalada desde tu tienda de aplicaciones.\n\n")
        append("Reinicia: Cierra la app por completo y, si es necesario, reinicia tu dispositivo.\n\n")
        append("Borra caché: En la configuración de tu teléfono, busca la app y borra su caché (no tus datos).\n\n")
        append("Contacta soporte: Si nada funciona, escríbenos con los detalles de tu problema.\n\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)) {
            append("Problemas de Pago:\n\n")
        }
        append("Datos Correctos: Verifica tu tarjeta/método de pago (número, fecha, CVV).\n\n")
        append("Buena Conexión: Asegúrate de tener Wi-Fi o datos estables.\n\n")
        append("Actualiza la App: Instala la última versión.\n\n")
        append("Saldo/Límite: Confirma que tengas fondos disponibles o contacta a tu banco.\n\n")
        append("Otro Método: Prueba con una tarjeta diferente si es posible.\n\n")
        append("Soporte: Si persiste, contáctanos con los detalles del error.\n\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)) {
            append("Invitar Amigos:\n\n")
        }
        append("Busca la Opción: Ve a tu perfil o menú y selecciona \"Invitar amigos\".\n\n")
        append("Elige cómo Compartir: Usa WhatsApp, SMS, email, etc., para enviar el enlace.\n\n")
        append("¡Que se Unan! Tus amigos solo deben hacer clic en el enlace para descargar y registrarse.")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.info_background),
            contentDescription = "Fondo de información",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa oscura para mejorar el contraste
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        // Contenido deslizable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()), // Hacemos que la columna sea deslizable
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = annotatedText,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Volver")
            }
        }
    }
}
