package com.example.koalit_recetas.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.koalit_recetas.screens.Recipe



class RecipeViewModel : ViewModel() {
    val recipes = mutableStateListOf<Recipe>()

    fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }
}
