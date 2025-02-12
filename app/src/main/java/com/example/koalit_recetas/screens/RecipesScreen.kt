package com.example.koalit_recetas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.koalit_recetas.R

@Composable
fun RecipeScreen(navController: NavHostController) {
    val ingredients = remember { mutableStateListOf("") }
    val steps = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFA726), RoundedCornerShape(0.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { /* Acción para subir imagen */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pasta),
                        contentDescription = "Subir Imagen",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Nombre (obligatorio)", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                    Text("Inserte una breve descripción")
                    Text("Tiempo en minutos...")
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nombre", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Ingredientes:")
            LazyColumn {
                items(ingredients.size) { index ->
                    OutlinedTextField(
                        value = ingredients[index],
                        onValueChange = { newValue -> ingredients[index] = newValue },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Ingrediente ${index + 1}") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { ingredients.add("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(25.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD2800B), // Naranja
                    contentColor = Color.White // Texto en blanco
                )
            ) {
                Text("Agregar Ingrediente", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Pasos")
            OutlinedTextField(
                value = steps.value,
                onValueChange = { steps.value = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                label = { Text("Ingrese los pasos de la receta") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { /* Guardar acción */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
            ) {
                Text("Guardar")
            }
            Button(
                onClick = { /* Eliminar acción */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
fun CustomTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Crea tu receta",
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)

        )
    }
}
