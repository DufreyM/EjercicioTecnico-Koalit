package com.example.koalit_recetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koalit_recetas.viewModel.RecipeViewModel
import com.example.koalit_recetas.data.SessionManager
import com.example.koalit_recetas.screens.LoginScreen
import com.example.koalit_recetas.screens.MainScreen
import com.example.koalit_recetas.screens.Pantalla_Carga
import com.example.koalit_recetas.screens.RecipeScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val sessionManager = remember { SessionManager(applicationContext) }
            val recipeViewModel: RecipeViewModel = viewModel() // ViewModel global

            // Determinar la pantalla inicial de manera síncrona
            val startDestination = runBlocking {
                if (sessionManager.isLoggedIn.first()) "main_screen" else "login_screen"
            }

            NavHost(navController = navController, startDestination = "pantalla_carga") {
                composable("pantalla_carga") { Pantalla_Carga(navController, startDestination) }
                composable("login_screen") { LoginScreen(navController) }
                composable("main_screen") { MainScreen(navController, recipeViewModel) }
                composable("recipe_screen") { RecipeScreen(navController, recipeViewModel) }
            }
        }
    }
}
