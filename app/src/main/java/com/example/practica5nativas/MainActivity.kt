package com.example.practica5nativas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5nativas.ejercicio2.adapter.BookAdapter
import com.example.practica5nativas.ejercicio2.adapter.BookLoadStateAdapter
import com.example.practica5nativas.ejercicio2.api.ApiClient
import com.example.practica5nativas.ejercicio2.data.SearchHistoryEntity
import com.example.practica5nativas.ejercicio2.viewmodel.BookViewModel
import com.example.practica5nativas.ejercicio2.viewmodel.BookViewModelFactory
import com.example.practica5nativas.ejercicio2.viewmodel.SearchHistoryViewModel
import com.example.practica5nativas.ejercicio2.FavoritesActivity
import com.example.practica5nativas.ejercicio2.HistoryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var emptyView: TextView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookViewModel: BookViewModel
    private lateinit var historyViewModel: SearchHistoryViewModel
    private var lastSavedQuery: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "API de Libros"

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        emptyView = findViewById(R.id.emptyView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val factory = BookViewModelFactory(ApiClient.apiService)
        bookViewModel = ViewModelProvider(this, factory)[BookViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[SearchHistoryViewModel::class.java]

        fetchBooks("Android")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchBooks(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    fetchBooks("Android")
                }
                return true
            }
        })
    }

    private fun fetchBooks(query: String) {
        bookAdapter = BookAdapter(this)
        recyclerView.adapter = bookAdapter.withLoadStateFooter(
            footer = BookLoadStateAdapter { bookAdapter.retry() }
        )

        lifecycleScope.launch {
            bookViewModel.searchBooks(query).collectLatest { pagingData ->
                bookAdapter.submitData(lifecycle, pagingData)
            }
        }

        lifecycleScope.launch {
            bookAdapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty =
                    loadStates.refresh is LoadState.NotLoading && bookAdapter.itemCount == 0
                emptyView.visibility = if (isListEmpty) View.VISIBLE else View.GONE
            }
        }

        bookAdapter.addLoadStateListener { loadState ->
            val isLoaded = loadState.refresh is LoadState.NotLoading
            if (isLoaded && bookAdapter.itemCount > 0) {
                val firstBook = bookAdapter.snapshot().items.firstOrNull()
                if (firstBook != null && lastSavedQuery != query) {
                    lastSavedQuery = query  // ← evita duplicados
                    historyViewModel.insert(
                        SearchHistoryEntity(
                            query = query,
                            firstBookTitle = firstBook.title,
                            firstBookCover = firstBook.getCoverUrl()
                        )
                    )
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            R.id.action_history -> {
                startActivityForResult(Intent(this, HistoryActivity::class.java), 1001)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val query = data?.getStringExtra(HistoryActivity.EXTRA_QUERY)
            if (!query.isNullOrEmpty()) {
                searchView.setQuery(query, true)
            }
        }
    }
}
