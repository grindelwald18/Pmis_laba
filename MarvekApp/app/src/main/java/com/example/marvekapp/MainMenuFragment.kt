package com.example.marvekapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

//import androidx.navigation.Navigation

class MainMenuFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Подключение к кнопке About
        val aboutButton = view.findViewById<Button>(R.id.aboutButton)
        aboutButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_aboutFragment)
        }

        val moviesList: RecyclerView = view.findViewById(R.id.moviesList)
        val movies = arrayListOf<Movie>()

        movies.add(Movie(1, "Ex1", "qwerty", "qwerty", LocalDate.of(2020, 2,3)))
        movies.add(Movie(1, "Ex2", "qwerty", "qwerty", LocalDate.of(2020, 2,3)))
        movies.add(Movie(1, "Ex3", "qwerty", "qwerty", LocalDate.of(2020, 2,3)))

        moviesList.layoutManager = LinearLayoutManager(this.context)
        moviesList.adapter = this.context?.let { MovieAdapter(movies, it) }
    }
}