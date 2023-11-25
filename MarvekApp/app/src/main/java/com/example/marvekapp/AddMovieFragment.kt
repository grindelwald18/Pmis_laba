package com.example.marvekapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddMovieFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_movie, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        val currentMovie = movieViewModel.getSelectedMovie().value

        val titleEditText: EditText = view.findViewById(R.id.edit_text_title)
        val descEditText: EditText = view.findViewById(R.id.edit_text_desc)
        val datePicker: DatePicker = view.findViewById(R.id.date_picker)
        val imageEditText: EditText = view.findViewById(R.id.edit_text_image)
        val progressbar: ProgressBar = view.findViewById(R.id.progress)

        movieViewModel.loading.observe(viewLifecycleOwner){isLoading->
            progressbar.isVisible = isLoading

        }

        lifecycleScope.launch {
            movieViewModel.navigateEventState.collect{
                findNavController().navigate(R.id.action_addMovieFragment_to_mainMenuFragment)
            }
        }

        if (currentMovie != null)
        {
            titleEditText.setText(currentMovie.title)
            descEditText.setText(currentMovie.desc)
            datePicker.updateDate(currentMovie.date.year, currentMovie.date.monthValue, currentMovie.date.dayOfMonth)
            imageEditText.setText(currentMovie.image)
        }

        val addButton: Button = view.findViewById(R.id.save_button)
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val desc = descEditText.text.toString()
            val date = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
            val image = imageEditText.text.toString()

            val movie = Movie(
                id = 0,
                image = image,
                title = title,
                desc = desc,
                date = date
            )

            if (currentMovie != null)
            {
                movie.id = currentMovie!!.id
                movieViewModel.updateMovie(movie)
                movieViewModel.selectMovie(null)
            }
            else
            {
                movieViewModel.addMovie(movie)
            }

        }
    }

}