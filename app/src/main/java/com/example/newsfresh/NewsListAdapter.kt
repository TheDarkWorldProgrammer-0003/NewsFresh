package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<News> = ArrayList()


    // it is called when the view holder is created
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsViewHolder {
        //layout inflator is used to convert the xml(here: item_news.xml) file into view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        return NewsViewHolder(view)


    }

    // it binds the data to the view holder
    override fun onBindViewHolder(
        holder: NewsViewHolder, position: Int
    ) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.author).into(holder.image)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(currentItem)


        }
    }

    // it will only call once and tell the size of the list/count of items in the list
    override fun getItemCount(): Int {
        return items.size

    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear() // Clear the old data
        items.addAll(updatedNews) // Add the new data
        notifyDataSetChanged() // Refresh the RecyclerView
    }
}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)


}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}

