package com.example.practica5nativas.ejercicio2.model
data class Book(
    val title: String,
    val author_name: List<String>?,
    val cover_i: Int? // ID de la imagen de portada
) {
    fun getCoverUrl(): String {
        return "https://covers.openlibrary.org/b/id/$cover_i-M.jpg"
    }
}
