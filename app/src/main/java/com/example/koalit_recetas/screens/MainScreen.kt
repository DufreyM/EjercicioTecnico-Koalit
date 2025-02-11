package com.example.koalit_recetas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController? = null) {
    var favoriteFilter by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }

    Scaffold(
        topBar = {
            CustomTopBar(favoriteFilter, sortOrder, { favorite -> favoriteFilter = favorite }, { order -> sortOrder = order })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navegar a agregar receta */ },
                containerColor = Color(0xFFE19A1C),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            RecipeList(favoriteFilter, sortOrder)
        }
    }
}

@Composable
fun CustomTopBar(
    favoriteFilter: Boolean,
    sortOrder: SortOrder,
    onFavoriteFilterChanged: (Boolean) -> Unit,
    onSortOrderChanged: (SortOrder) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEB35A))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recetas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        // Filtro de favoritos
        IconToggleButton(checked = favoriteFilter, onCheckedChange = onFavoriteFilterChanged) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Filtrar favoritos",
                tint = if (favoriteFilter) Color.Red else Color.Gray
            )
        }

        // Ordenar por tiempo
        IconButton(onClick = {
            onSortOrderChanged(if (sortOrder == SortOrder.Ascending) SortOrder.Descending else SortOrder.Ascending)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = "Ordenar por tiempo"
            )
        }

        // Cerrar sesión
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "Cerrar sesión",
            modifier = Modifier
                .size(28.dp)
                .clickable { /* Acción de cerrar sesión */ }
        )
    }
}

@Composable
fun RecipeList(favoriteFilter: Boolean, sortOrder: SortOrder, modifier: Modifier = Modifier) {
    // Lista vacía de recetas
    val recipes = emptyList<Recipe>()

    val filteredRecipes = recipes.filter { it.isFavorite == favoriteFilter || !favoriteFilter }

    val sortedRecipes = when (sortOrder) {
        SortOrder.Ascending -> filteredRecipes.sortedBy { it.time }
        SortOrder.Descending -> filteredRecipes.sortedByDescending { it.time }
    }

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(sortedRecipes.size) { index ->
            RecipeItem(sortedRecipes[index])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { /* Ver detalles */ },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = recipe.image), contentDescription = null, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(recipe.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(recipe.description, fontSize = 14.sp, color = Color.Gray)
            }
            Text("${recipe.time} min", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

data class Recipe(val title: String, val description: String, val time: Int, val image: Int, var isFavorite: Boolean)

enum class SortOrder { Ascending, Descending }

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
