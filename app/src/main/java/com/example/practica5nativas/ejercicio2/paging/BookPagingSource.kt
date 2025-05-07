package com.example.practica5nativas.ejercicio2.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practica5nativas.ejercicio2.api.OpenLibraryService
import com.example.practica5nativas.ejercicio2.model.Book

class BookPagingSource(
    private val apiService: OpenLibraryService,
    private val query: String
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val page = params.key ?: 1
        val limit = params.loadSize
        val offset = (page - 1) * limit

        return try {
            val response = apiService.searchBooks(query, limit, offset)
            val books = response.body()?.docs ?: emptyList()
            Log.d("PAGING", "Cargando página: $page con offset $offset y límite $limit")
            Log.d("PAGING", "Respuesta recibida: ${response.body()?.docs?.size ?: 0} libros")

            LoadResult.Page(
                data = books,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (books.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
