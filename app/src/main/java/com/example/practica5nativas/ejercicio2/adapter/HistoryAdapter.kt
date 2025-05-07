package com.example.practica5nativas.ejercicio2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica5nativas.R
import com.example.practica5nativas.ejercicio2.data.SearchHistoryEntity

class HistoryAdapter(
    private val historyList: List<SearchHistoryEntity>,
    private val onItemClick: (SearchHistoryEntity) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val queryText: TextView = view.findViewById(R.id.historyQuery)
        val bookTitle: TextView = view.findViewById(R.id.historyBookTitle)
        val bookCover: ImageView = view.findViewById(R.id.historyBookCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val entry = historyList[position]
        holder.queryText.text = entry.query
        holder.bookTitle.text = entry.firstBookTitle

        Glide.with(holder.itemView.context)
            .load(entry.firstBookCover)
            .placeholder(R.drawable.placeholder)
            .into(holder.bookCover)

        holder.itemView.setOnClickListener {
            onItemClick(entry)
        }
    }

    override fun getItemCount(): Int = historyList.size
}
