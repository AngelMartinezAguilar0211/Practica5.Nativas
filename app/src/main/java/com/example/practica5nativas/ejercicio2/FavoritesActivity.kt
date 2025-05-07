package com.example.practica5nativas.ejercicio2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5nativas.ejercicio2.adapter.FavoriteAdapter
import com.example.practica5nativas.ejercicio2.viewmodel.FavoriteViewModel
import com.example.practica5nativas.R

class FavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val toolbar: Toolbar = findViewById(R.id.toolbarFavorites)
        toolbar.title = "Favoritos"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter(viewModel)
        recyclerView.adapter = adapter

        viewModel.allFavorites.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
