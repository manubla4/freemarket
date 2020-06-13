package com.manubla.cinemapp.presentation.view.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.databinding.ItemMovieBinding

class FavoritesAdapter(private val listener: OnAdapterInteraction):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var movies = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(ItemMovieBinding.inflate(inflater))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder && movies[position] is Movie)
            holder.bind(movies[position] as Movie)
    }

    inner class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?) {
            binding.movie = item
            binding.cardMovie.setOnClickListener {
                listener.onSelectMovie(item)
            }
            binding.executePendingBindings()
        }
    }


    interface OnAdapterInteraction {
        fun onSelectMovie(movie: Movie?)
    }
}