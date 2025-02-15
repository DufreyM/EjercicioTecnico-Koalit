package com.example.koalit_recetas.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.koalit_recetas.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeEntity>> get() = _recipes

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        fetchRecipes()
    }

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getAllRecipes()
        }
    }

    fun addRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.insertRecipe(recipe)
            fetchRecipes()
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
            fetchRecipes()
        }
    }
}
