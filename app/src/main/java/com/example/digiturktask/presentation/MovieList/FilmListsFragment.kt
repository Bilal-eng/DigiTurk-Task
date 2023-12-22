package com.example.digiturktask.presentation.MovieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.digiturktask.R
import com.example.digiturktask.data.data_source.dto.GenresListDTO.Genre
import com.example.digiturktask.databinding.FragmentFilmListsBinding
import com.example.digiturktask.presentation.MovieList.adapter.DepthPageTransformer
import com.example.digiturktask.presentation.MovieList.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FilmListsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var _binding: FragmentFilmListsBinding? = null
    private val binding get() = _binding
    private val movieListViewModel: MovieListViewModel by viewModels()
    private val tempMovieList = arrayListOf<Genre>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmListsBinding.inflate(inflater, container, false)
        val view = binding?.root
        view?.let {
            viewPager = it.findViewById(R.id.viewPager)
            tabLayout = it.findViewById(R.id.tabLayout)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callAPI()
    }

    private fun callAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            movieListViewModel.getMovieGenres()
            movieListViewModel._movieListValue.collectLatest { movieListValue ->

                withContext(Dispatchers.Main) {
                    if (movieListValue.isLoading) {

                    } else {
                        if (movieListValue.error.isNotBlank()) {
                            Toast.makeText(
                                requireContext(),
                                movieListValue.error,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            tempMovieList.addAll(movieListValue.genres)
                            viewPager.adapter =
                                ViewPagerAdapter(tempMovieList, this@FilmListsFragment)
                            viewPager.setPageTransformer(DepthPageTransformer())
                            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                                tempMovieList.let {
                                    tab.text = it[position].name
                                }
                            }.attach()
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