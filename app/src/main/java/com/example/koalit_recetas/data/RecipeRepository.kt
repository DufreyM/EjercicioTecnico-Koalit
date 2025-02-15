package com.example.koalit_recetas.data

class RecipeRepository(private val recipeDao: RecipeDao) {

    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun getAllRecipes(): List<RecipeEntity> {
        return recipeDao.getAllRecipes()
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        recipeDao.deleteRecipe(recipe)
    }

    suspend fun deleteAllRecipes() {
        recipeDao.deleteAllRecipes()
    }
}
