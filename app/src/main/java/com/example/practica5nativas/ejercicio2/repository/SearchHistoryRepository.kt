package com.example.practica5nativas.ejercicio2.repository

import com.example.practica5nativas.ejercicio2.data.SearchHistoryDao
import com.example.practica5nativas.ejercicio2.data.SearchHistoryEntity

class SearchHistoryRepository(private val dao: SearchHistoryDao) {

    val allHistory = dao.getAll()

    suspend fun insert(entry: SearchHistoryEntity) {
        dao.insert(entry)
    }

    suspend fun clearAll() {
        dao.clearAll()
    }
}
