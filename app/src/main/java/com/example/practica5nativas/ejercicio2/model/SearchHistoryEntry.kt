package com.example.practica5nativas.ejercicio2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val firstBookTitle: String,
    val firstBookCover: String?
)
