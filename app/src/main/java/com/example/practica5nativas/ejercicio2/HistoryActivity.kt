package com.example.practica5nativas.ejercicio2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5nativas.ejercicio2.adapter.HistoryAdapter
import com.example.practica5nativas.ejercicio2.viewmodel.SearchHistoryViewModel
import com.example.practica5nativas.R

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "search_query"
    }

    private val viewModel: SearchHistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar: Toolbar = findViewById(R.id.toolbarHistory)
        toolbar.title = "Historial de Búsquedas"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.history.observe(this) { list ->
            adapter = HistoryAdapter(list) { selected ->
                val resultIntent = Intent().putExtra(EXTRA_QUERY, selected.query)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            recyclerView.adapter = adapter
        }
    }
}
