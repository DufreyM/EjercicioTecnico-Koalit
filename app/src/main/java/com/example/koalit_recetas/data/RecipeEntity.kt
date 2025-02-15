package com.example.koalit_recetas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.koalit_recetas.screens.Recipe

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val time: Int,
    val image: String?,
    val isFavorite: Boolean,
    val pasos: String,
    val ingredientes: String // Guardamos la lista como un String (convertimos JSON)
)

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        title = this.title,
        description = this.description,
        time = this.time,
        image = this.image,
        isFavorite = this.isFavorite,
        pasos = this.pasos,
        ingredientes = this.ingredientes.split(",") // Convierte de String a List
    )
}

