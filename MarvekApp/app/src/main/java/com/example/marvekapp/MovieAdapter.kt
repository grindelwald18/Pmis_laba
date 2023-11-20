package com.example.marvekapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(var movies: List<Movie>, var context: Context): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val image: ImageView = view.findViewById(R.id.movie_image)
        val title: TextView = view.findViewById(R.id.movie_title)
        val desc: TextView = view.findViewById(R.id.movie_desc)
        val date: TextView = view.findViewById(R.id.movie_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = movies[position].title
        holder.desc.text = movies[position].desc
        holder.date.text = movies[position].date.toString()
    }
}