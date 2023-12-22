package com.example.digiturktask.presentation.Movies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.digiturktask.R
import com.example.digiturktask.databinding.MovieItemLayoutBinding
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.Result
import com.example.digiturktask.util.Constants.POSTER_IMAGE_URL

class MovieItemAdapter(
    private val itemList: List<Result>,
    private val onItemClick: (Result) -> Unit
) : RecyclerView.Adapter<MovieItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(private val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Result, onItemClick: (Result) -> Unit) {
            binding.apply {
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
                val imageUrl = POSTER_IMAGE_URL + item.poster_path
                Log.d("ImageUrl", imageUrl)
                Glide.with(itemView)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                textViewTitle.text = item.title

                itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }
}