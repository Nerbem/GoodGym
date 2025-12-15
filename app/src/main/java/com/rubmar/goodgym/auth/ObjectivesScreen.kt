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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rubmar.goodgym.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectivesScreen(navController: NavController) {

    var currentWeight by remember { mutableStateOf("") }
    var targetWeight by remember { mutableStateOf("") }
    val timeOptions = listOf("6", "9", "12")
    var weeklyTime by remember { mutableStateOf(timeOptions[0]) }
    var isTimeExpanded by remember { mutableStateOf(false) }
    var healthCondition by remember { mutableStateOf<Boolean?>(null) }
    var medication by remember { mutableStateOf<Boolean?>(null) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = Color.White,
        focusedTextColor = Color.White,
        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
        focusedLabelColor = Color.White,
        unfocusedBorderColor = Color.Gray,
        focusedBorderColor = Color.White,
        cursorColor = Color.White,
        focusedContainerColor = Color.DarkGray.copy(alpha = 0.5f),
        unfocusedContainerColor = Color.DarkGray.copy(alpha = 0.5f)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cuestionario de Objetivos",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
            )

            OutlinedTextField(
                value = currentWeight,
                onValueChange = { currentWeight = it },
                label = { Text("¿Cuánto pesas? (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = targetWeight,
                onValueChange = { targetWeight = it },
                label = { Text("¿Cuál es tu peso objetivo? (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = isTimeExpanded,
                onExpandedChange = { isTimeExpanded = !isTimeExpanded }
            ) {
                OutlinedTextField(
                    value = "$weeklyTime horas",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("¿Tiempo a la semana?") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTimeExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    colors = textFieldColors
                )
                ExposedDropdownMenu(
                    expanded = isTimeExpanded,
                    onDismissRequest = { isTimeExpanded = false }
                ) {
                    timeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text("$option horas") },
                            onClick = {
                                weeklyTime = option
                                isTimeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            YesNoSelector(
                question = "¿Alguna condición física o de salud?",
                selectedOption = healthCondition,
                onOptionSelected = { healthCondition = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            YesNoSelector(
                question = "¿Tomas alguna medicación?",
                selectedOption = medication,
                onOptionSelected = { medication = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
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
                    onClick = { navController.navigate("variations") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}

@Composable
fun YesNoSelector(
    question: String,
    selectedOption: Boolean?,
    onOptionSelected: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(question, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { onOptionSelected(true) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption == true) Color.White else Color.DarkGray.copy(alpha = 0.5f),
                    contentColor = if (selectedOption == true) Color.Black else Color.White
                )
            ) {
                Text("Sí")
            }
            Button(
                onClick = { onOptionSelected(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption == false) Color.White else Color.DarkGray.copy(alpha = 0.5f),
                    contentColor = if (selectedOption == false) Color.Black else Color.White
                )
            ) {
                Text("No")
            }
        }
    }
}