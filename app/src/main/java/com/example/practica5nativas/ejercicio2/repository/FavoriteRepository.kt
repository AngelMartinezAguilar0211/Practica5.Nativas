package com.example.practica5nativas.ejercicio2.repository

import com.example.practica5nativas.ejercicio2.data.FavoriteBook
import com.example.practica5nativas.ejercicio2.data.FavoriteBookDao

class FavoriteRepository(private val dao: FavoriteBookDao) {

    val allFavorites = dao.getAll()

    suspend fun add(book: FavoriteBook) {
        dao.insert(book)
    }

    suspend fun remove(book: FavoriteBook) {
        dao.delete(book)
    }

    suspend fun isFavorite(key: String): Boolean {
        return dao.isFavorite(key)
    }
}
