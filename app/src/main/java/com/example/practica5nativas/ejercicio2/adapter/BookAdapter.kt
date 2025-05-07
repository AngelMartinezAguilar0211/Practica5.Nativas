package com.example.practica5nativas.ejercicio2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica5nativas.R
import com.example.practica5nativas.ejercicio2.data.FavoriteBook
import com.example.practica5nativas.ejercicio2.model.Book
import com.example.practica5nativas.ejercicio2.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookAdapter(
    private val viewModelOwner: ViewModelStoreOwner
) : PagingDataAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(viewModelOwner)[FavoriteViewModel::class.java]
    }

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookCover: ImageView = view.findViewById(R.id.bookCover)
        val bookTitle: TextView = view.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthor)
        val favButton: ImageButton = view.findViewById(R.id.favButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        if (book != null) {
            holder.bookTitle.text = book.title
            holder.bookAuthor.text = book.author_name?.joinToString(", ") ?: "Autor desconocido"

            Glide.with(holder.itemView.context)
                .load(book.getCoverUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.bookCover)

            holder.favButton.setOnClickListener {
                val favorite = FavoriteBook(
                    key = (book.cover_i ?: book.title).toString(), // fallback si key es null
                    title = book.title,
                    authors = book.author_name?.joinToString(", "),
                    coverUrl = book.getCoverUrl()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val alreadyFav = viewModel.isFavorite(favorite.key)
                    if (!alreadyFav) {
                        viewModel.addFavorite(favorite)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                holder.itemView.context,
                                "Agregado a favoritos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                holder.itemView.context,
                                "Ya está en favoritos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.cover_i == newItem.cover_i
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}
