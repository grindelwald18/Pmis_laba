package com.example.marvekapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class DetailsFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        val currentMovie = movieViewModel.getSelectedMovie()

        val deleteButton = view.findViewById<Button>(R.id.movie_delete)
        deleteButton.setOnClickListener {
            currentMovie.value?.let { it1 -> movieViewModel.removeMovie(it1.id) }

            findNavController().navigate(R.id.action_detailsFragment_to_mainMenuFragment)
        }

        val updateButton = view.findViewById<Button>(R.id.movie_update)
        updateButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_addMovieFragment)
        }

        val title = view.findViewById<TextView>(R.id.movie_title)
        val desc = view.findViewById<TextView>(R.id.movie_desc)
        val date = view.findViewById<TextView>(R.id.movie_date)
        val image = view.findViewById<ImageView>(R.id.movie_image)

        currentMovie.observe(viewLifecycleOwner) { movie ->
            title.text = movie!!.title
            desc.text = movie.desc
            date.text = movie.date.toString()

            context?.resources?.let {
                image.setImageResource(
                    it.getIdentifier(
                        movie.image,
                        "drawable",
                        requireContext().opPackageName
                    ))
            }
        }
    }
}