package com.example.practica5nativas.ejercicio2.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.practica5nativas.ejercicio2.data.AppDatabase
import com.example.practica5nativas.ejercicio2.data.FavoriteBook
import com.example.practica5nativas.ejercicio2.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).favoriteBookDao()
    private val repository = FavoriteRepository(dao)

    val allFavorites: LiveData<List<FavoriteBook>> = repository.allFavorites

    fun addFavorite(book: FavoriteBook) = viewModelScope.launch {
        repository.add(book)
    }

    fun removeFavorite(book: FavoriteBook) = viewModelScope.launch {
        repository.remove(book)
    }

    suspend fun isFavorite(key: String): Boolean {
        return repository.isFavorite(key)
    }
    

}
