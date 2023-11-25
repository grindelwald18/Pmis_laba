package com.example.marvekapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private var movies: List<Movie>, private val movieViewModel: MovieViewModel, var context: Context): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val image: ImageView = view.findViewById(R.id.movie_image)
        val title: TextView = view.findViewById(R.id.movie_title)
        val date: TextView = view.findViewById(R.id.movie_date)
        val detailsButton: Button = view.findViewById(R.id.detailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = movies[position].title
        holder.date.text = movies[position].date.toString()

        var imageId = 0;
        if (movies[position].image != "")
        {
            imageId = context.resources.getIdentifier(
                movies[position].image,
                "drawable",
                context.opPackageName
            )
        }
        if (imageId == 0)
        {
            imageId = context.resources.getIdentifier(
                "pack",
                "drawable",
                context.opPackageName
            )
        }

        holder.image.setImageResource(imageId)

        val movie = movies[position]

        holder.detailsButton.setOnClickListener{
            movieViewModel.selectMovie(movie)
            holder.itemView.findNavController().navigate(R.id.action_mainMenuFragment_to_detailsFragment)
        }
    }
}