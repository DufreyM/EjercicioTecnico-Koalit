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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.koalit_recetas.R
import com.example.koalit_recetas.viewModel.RecipeViewModel
import com.example.koalit_recetas.data.SessionManager
import kotlinx.coroutines.launch

//Faltantes, cuando se clickea una receta esta no abre los detalles.

@Composable
fun MainScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    var favoriteFilter by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) } // Estado para el modal
    val recipes by recipeViewModel.recipes.collectAsState()
    val convertedRecipes = recipes.map { entity ->
        Recipe(
            title = entity.title,
            description = entity.description,
            time = entity.time,
            image = entity.image,
            isFavorite = entity.isFavorite,
            pasos = entity.pasos,
            ingredientes = entity.ingredientes.split(",") // Convierte el String en una lista
        )
    }

    Scaffold(
        topBar = { CustomTopBar(favoriteFilter, sortOrder, { favoriteFilter = it }, { sortOrder = it }, navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("recipe_screen") },
                containerColor = Color(0xFFE19A1C),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            RecipeList(
                recipes = convertedRecipes,
                favoriteFilter = favoriteFilter,
                sortOrder = sortOrder,
                onRecipeClick = { selectedRecipe = it }
            )
        }
    }

    // Muestra los detalles de la receta en un diálogo si se ha seleccionado una receta
    selectedRecipe?.let { recipe ->
        RecipeDetailsDialog(recipe, onDismiss = { selectedRecipe = null })
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>, favoriteFilter: Boolean, sortOrder: SortOrder, onRecipeClick: (Recipe) -> Unit) {
    val filteredRecipes = recipes.filter { it.isFavorite == favoriteFilter || !favoriteFilter }
    val sortedRecipes = when (sortOrder) {
        SortOrder.Ascending -> filteredRecipes.sortedBy { it.time }
        SortOrder.Descending -> filteredRecipes.sortedByDescending { it.time }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(sortedRecipes.size) { index ->
            RecipeItem(sortedRecipes[index], onClick = { onRecipeClick(sortedRecipes[index]) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CustomTopBar(
    favoriteFilter: Boolean,
    sortOrder: SortOrder,
    onFavoriteFilterChanged: (Boolean) -> Unit,
    onSortOrderChanged: (SortOrder) -> Unit,
    navController: NavHostController // Se pasa el NavController
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800))
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

        IconToggleButton(checked = favoriteFilter, onCheckedChange = onFavoriteFilterChanged) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Filtrar favoritos",
                tint = if (favoriteFilter) Color.Red else Color.Gray
            )
        }

        IconButton(onClick = {
            onSortOrderChanged(if (sortOrder == SortOrder.Ascending) SortOrder.Descending else SortOrder.Ascending)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = "Ordenar por tiempo"
            )
        }

        // Botón de Cerrar Sesión
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "Cerrar sesión",
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    coroutineScope.launch {
                        sessionManager.clearSession()
                        navController.navigate("login_screen") {
                            popUpTo("main_screen") { inclusive = true }
                        }
                    }
                }
        )
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Se activa el modal
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            val painter = if (recipe.image?.startsWith("content://") == true) {
                rememberAsyncImagePainter(recipe.image) // Carga desde URI
            } else {
                painterResource(id = R.drawable.default_image)
            }

            Image(painter = painter, contentDescription = null, modifier = Modifier.size(64.dp))

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(recipe.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(recipe.description, fontSize = 14.sp, color = Color.Gray)
            }

            Text("${recipe.time} min", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun RecipeDetailsDialog(recipe: Recipe, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(recipe.title, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                val painter = if (recipe.image?.startsWith("content://") == true) {
                    rememberAsyncImagePainter(recipe.image)
                } else {
                    painterResource(id = R.drawable.default_image)
                }

                Image(painter = painter, contentDescription = null, modifier = Modifier.size(120.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Descripción:", fontWeight = FontWeight.Bold)
                Text(text = recipe.description, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Tiempo: ${recipe.time} min", fontWeight = FontWeight.Bold)

                Text(text = "Pasos:", fontWeight = FontWeight.Bold)
                Text(text = recipe.pasos, fontSize = 16.sp)

            }
        },
        confirmButton = {
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),) {
                Text("Cerrar")
            }
        }
    )
}

data class Recipe(val title: String, val description: String, val time: Int, val image: String?, var isFavorite: Boolean, val pasos: String, val ingredientes: List<String>)

enum class SortOrder { Ascending, Descending }

