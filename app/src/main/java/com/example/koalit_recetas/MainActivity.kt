package com.example.koalit_recetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koalit_recetas.screens.LoginScreen
import com.example.koalit_recetas.screens.MainScreen
import com.example.koalit_recetas.screens.Pantalla_Carga

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loading") {
        composable("loading") { Pantalla_Carga(navController) }
        composable("login") { LoginScreen(navController) }
        composable("main") { MainScreen(navController) }
    }
}


