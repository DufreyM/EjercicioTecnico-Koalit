package com.example.koalit_recetas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.koalit_recetas.R
import com.example.koalit_recetas.viewModel.RecipeViewModel
import androidx.compose.ui.text.input.KeyboardType

//Me falta cambiar cuando ingredientes se agregan más de 3, el botón más y cancelar desaparecen y ya no se puede agregar la receta COMPLETO
//Validar que en el tiempo solo se puedan ingresar números Completo
//Modificar la foto, por el momento muestra una foto quemada de pasta.
//Agregar el icono de estrella para poder marcar recetas como favoritas.
//Usar ROOM Database para almacenar los datos localmente.

@Composable
fun RecipeScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf("") }
    val steps = remember { mutableStateOf("") }
    val isFavorite = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        CustomTopBar(isFavorite)

        Text("Nombre de la receta", fontWeight = FontWeight.Bold)
        OutlinedTextField(value = name.value, onValueChange = { name.value = it }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Text("Descripción")
        OutlinedTextField(value = description.value, onValueChange = { description.value = it }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Text("Tiempo de preparación (min)")
        OutlinedTextField(
            value = time.value,
            onValueChange = { if (it.all { char -> char.isDigit() }) time.value = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Ingredientes:")

        Box(modifier = Modifier.weight(2f)) {  // Permite que la lista se expanda sin empujar los botones
            LazyColumn(
                modifier = Modifier.fillMaxHeight(1f) // Establece un tamaño máximo desplazable
            ) {
                items(ingredients.size) { index ->
                    OutlinedTextField(
                        value = ingredients[index],
                        onValueChange = { ingredients[index] = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Ingrediente ${index + 1}") }
                    )
                }
            }
        }

        Button(
            onClick = { ingredients.add("") }, // Agregar ingrediente
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text("Agregar Ingrediente")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Pasos")
        OutlinedTextField(
            value = steps.value,
            onValueChange = { steps.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Sección de botones siempre visible
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    if (name.value.isNotEmpty() && time.value.isNotEmpty()) {
                        val newRecipe = Recipe(
                            title = name.value,
                            description = description.value,
                            time = time.value.toIntOrNull() ?: 0,
                            image = R.drawable.pasta,
                            isFavorite = isFavorite.value
                        )

                        recipeViewModel.addRecipe(newRecipe)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
            ) {
                Text("Guardar")
            }
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Cancelar")
            }
        }
    }
}


@Composable
fun CustomTopBar(isFavorite: MutableState<Boolean>) {
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
        IconButton(onClick = { isFavorite.value = !isFavorite.value }) {
            Icon(
                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "Marcar como favorita",
                tint = if (isFavorite.value) Color.Red else Color.Gray,
                modifier = Modifier
                    .size(32.dp)
            )
        }
    }
}

