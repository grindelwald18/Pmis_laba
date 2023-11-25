package com.example.marvekapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class MainMenuFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        movieViewModel.selectMovie(null)

        val aboutButton = view.findViewById<Button>(R.id.aboutButton)
        aboutButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_aboutFragment)
        }

        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_addMovieFragment)
        }

        val moviesList: RecyclerView = view.findViewById(R.id.moviesList)

        moviesList.layoutManager = LinearLayoutManager(this.context)

        movieViewModel.getData().observe(viewLifecycleOwner) { movieList ->
            moviesList.adapter = context?.let { MovieAdapter(movieList, movieViewModel, it) }
        }
    }
}