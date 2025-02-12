package com.example.koalit_recetas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun Pantalla_Carga(navController: NavHostController) {

    //Corrección de pantalla de carga, para que duré solo un tiempo.
    LaunchedEffect(Unit) {
        delay(3000) // Espera 3 segundos
        navController.navigate("login") {
            popUpTo("loading") { inclusive = true } // Elimina la pantalla
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE79313), Color(0xFFAF9A4B))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icono de cocina
            Icon(
                imageVector = Icons.Default.SoupKitchen,
                contentDescription = "Cocina",
                tint = Color.White,
                modifier = Modifier.size(150.dp) // Icono grande
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto El arte del sabor
            Text(
                text = "El arte del sabor",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Solo para indicarle al usuario que algo se esta cargando y no que quedo congelada la pantalla
            CircularProgressIndicator(color = Color.White)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text("By: Koalit", color = Color.White, fontSize = 14.sp)
        }
    }
}
