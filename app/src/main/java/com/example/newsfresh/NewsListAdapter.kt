package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsListAdapter(private val items: ArrayList<String>, private val listener: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {
    // it is called when the view holder is created
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsViewHolder {
        //layout inflator is used to convert the xml(here: item_news.xml) file into view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        val viewHolder = NewsViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])

        }
        return viewHolder

    }

    // it binds the data to the view holder
    override fun onBindViewHolder(
        holder: NewsViewHolder, position: Int
    ) {
        val currentItem = items[position]
        holder.titleView.text = currentItem

    }

    // it will only call once and tell the size of the list/count of items in the list
    override fun getItemCount(): Int {
        return items.size

    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)

}

interface NewsItemClicked {
    fun onItemClicked(item: String)
}

