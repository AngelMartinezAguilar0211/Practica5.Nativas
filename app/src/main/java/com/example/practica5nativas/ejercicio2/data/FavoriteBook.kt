package com.example.practica5nativas.ejercicio2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey val key: String,
    val title: String,
    val authors: String?,
    val coverUrl: String?
)
