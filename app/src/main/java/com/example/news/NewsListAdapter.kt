package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()
    //Creating an instance of function interface in Argument
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        //called when a view holder is created, it will be called same times as the number
        //of views on screen. As Afterwards the views will be recycled
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        //LayoutInFlater is a class that converts anything from xml to view.
        // Here we have to inflate item_news

        val viewHolder: NewsViewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
            //Here we ll get position of viewholder in the adapter and use the same position
            //of the array of 'items' to get info on it
            //OnClicks needs to be handled by the main Activity
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        //Function which binds the data inside the holder
        //It fills data into its corresponding item
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        //Gets called the first time
        //tells the number of items in the list
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>) {      //This functions gives updated items to the adapter
        items.clear()       //Clears items if it already have something in it
        items.addAll(updatedNews)       //Adding updated news to the items

        notifyDataSetChanged()      //This would re-run all the 3 functions of adapter after getting updated
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    //Now the title would appear in item view
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}
//Interfaces is used as a call back. The adapter call backs main activity
//telling that the item is clicked. Main Activity handles the rest.