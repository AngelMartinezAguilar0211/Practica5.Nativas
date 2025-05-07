package com.example.practica5nativas.ejercicio2.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.practica5nativas.ejercicio2.data.AppDatabase
import com.example.practica5nativas.ejercicio2.data.SearchHistoryEntity
import com.example.practica5nativas.ejercicio2.repository.SearchHistoryRepository
import kotlinx.coroutines.launch

class SearchHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).searchHistoryDao()
    private val repository = SearchHistoryRepository(dao)

    val history: LiveData<List<SearchHistoryEntity>> = repository.allHistory

    fun insert(entry: SearchHistoryEntity) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }
    fun getHistoryNow(): List<SearchHistoryEntity> {
        return history.value ?: emptyList()
    }

}
