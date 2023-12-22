package com.example.digiturktask.presentation.Movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.Result
import com.example.digiturktask.databinding.FragmentMoviesBinding
import com.example.digiturktask.presentation.MovieList.FilmListsFragmentDirections
import com.example.digiturktask.presentation.Movies.adapter.MovieItemAdapter
import com.example.digiturktask.util.Constants.GENRE_ID
import com.example.digiturktask.util.Constants.VIDEO_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding
    private val moviesViewModel: MoviesViewModel by viewModels()
    private val tempMovieList = arrayListOf<Result>()
    private var movieItemAdapter: MovieItemAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(GENRE_ID) }?.apply {
            callApi(getInt(GENRE_ID))
        }
    }

    private fun callApi(genreId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesViewModel.getMoviesById(genreId)
            moviesViewModel._moviesValue.collectLatest { movieListValue ->
                withContext(Dispatchers.Main) {
                    if (movieListValue.isLoading) {
                        binding?.progressBar?.visibility = View.VISIBLE
                    } else {
                        if (movieListValue.error.isNotBlank()) {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                movieListValue.error,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            binding?.progressBar?.visibility = View.GONE
                            movieListValue.moviesListDTO?.results?.let {
                                tempMovieList.addAll(it)
                                movieItemAdapter = MovieItemAdapter(it) {
                                    val action = FilmListsFragmentDirections
                                        .actionFilmListsFragmentToVideoPlayerFragment(
                                            it.title, VIDEO_URL
                                        )
                                    findNavController().navigate(action)
                                }
                                binding?.moviesRv?.apply {
                                    layoutManager = GridLayoutManager(requireContext(), 2)
                                    adapter = movieItemAdapter
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}