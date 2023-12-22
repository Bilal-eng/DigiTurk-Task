package com.example.digiturktask.presentation.MovieList.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.digiturktask.data.data_source.dto.GenresListDTO.Genre
import com.example.digiturktask.presentation.Movies.MoviesFragment
import com.example.digiturktask.util.Constants.GENRE_ID

class ViewPagerAdapter(private val tempMovieList: ArrayList<Genre>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tempMovieList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = MoviesFragment()
        fragment.arguments = Bundle().apply {
            putInt(GENRE_ID, tempMovieList[position].id)
        }
        return fragment
    }
}