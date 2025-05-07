package com.example.practica5nativas.ejercicio2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica5nativas.R
import com.example.practica5nativas.ejercicio2.data.FavoriteBook
import com.example.practica5nativas.ejercicio2.viewmodel.FavoriteViewModel

class FavoriteAdapter(private val viewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites: List<FavoriteBook> = emptyList()

    fun submitList(list: List<FavoriteBook>) {
        favorites = list
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookCover: ImageView = view.findViewById(R.id.bookCover)
        val bookTitle: TextView = view.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthor)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val book = favorites[position]
        holder.bookTitle.text = book.title
        holder.bookAuthor.text = book.authors ?: "Autor desconocido"

        Glide.with(holder.itemView.context)
            .load(book.coverUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.bookCover)

        holder.deleteButton.setOnClickListener {
            viewModel.removeFavorite(book)
            Toast.makeText(holder.itemView.context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = favorites.size
}
