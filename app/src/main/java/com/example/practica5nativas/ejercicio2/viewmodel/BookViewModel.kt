package com.example.practica5nativas.ejercicio2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practica5nativas.ejercicio2.api.OpenLibraryService
import com.example.practica5nativas.ejercicio2.model.Book
import com.example.practica5nativas.ejercicio2.paging.BookPagingSource
import kotlinx.coroutines.flow.Flow

class BookViewModel(private val apiService: OpenLibraryService) : ViewModel() {

    fun searchBooks(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { BookPagingSource(apiService, query) }
        ).flow.cachedIn(viewModelScope)
    }
}
