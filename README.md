# Proyecto Koalit - Aplicación de Recetas

Este proyecto es una aplicación de recetas desarrollada en Android utilizando **Jetpack Compose**, **Room** para almacenamiento local, y **Firebase** para almacenamiento de datos en la nube. El propósito de esta aplicación es permitir a los usuarios explorar, agregar y administrar sus recetas favoritas.

## Funcionalidades

- Pantalla principal: Muestra la lista de recetas con la opción de filtrar por recetas favoritas y ordenar por tiempo.
- Agregar receta: Permite a los usuarios agregar nuevas recetas, incluyendo título, descripción, ingredientes, pasos, y tiempo de preparación. También se puede marcar como favorita.
- Detalles de receta: Al hacer clic en una receta, se muestra un diálogo con los detalles completos de la receta seleccionada.
- Autenticación: El usuario puede iniciar sesión en la aplicación, pero la opción de registro y recuperación de contraseña no está disponible por el momento. Al hacer clic en los botones correspondientes, aparecerá un mensaje indicando que no están disponibles.
- Almacenamiento local con Room: Las recetas se guardan localmente usando **Room**, incluyendo la imagen de la receta (almacenada como una URI).
- Almacenamiento de sesión con DataStore: Los datos de sesión del usuario se guardan de manera segura utilizando **DataStore**.

## Estructura de la Base de Datos

La base de datos de Room está compuesta por una tabla llamada `recipes`, que contiene los siguientes campos:

- **id** (autogenerado, clave primaria)
- **title** (título de la receta)
- **description** (descripción de la receta)
- **time** (tiempo de preparación)
- **image** (URI de la imagen)
- **isFavorite** (booleano para marcar como favorita)
- **pasos** (instrucciones de la receta)
- **ingredientes** (lista de ingredientes convertida a un string)

## Faltante

La imagen no se carga, pese a que se guarde su URL. 
Los botones de olvide contraseña y registrarse no se encuentran disponibles actualmente. 
Sería ideal agregar la opción de editar recetas.