package com.example.practica5nativas.ejercicio2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: FavoriteBook)

    @Delete
    suspend fun delete(book: FavoriteBook)

    @Query("SELECT * FROM favorite_books")
    fun getAll(): LiveData<List<FavoriteBook>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE key = :key)")
    suspend fun isFavorite(key: String): Boolean
}
