package com.manubla.restoya.presentation.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.databinding.ItemMovieBinding

class FavoritesAdapter(private val listener: OnAdapterInteraction):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var movies = listOf<Restaurant>()
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
        if (holder is MovieViewHolder && movies[position] is Restaurant)
            holder.bind(movies[position] as Restaurant)
    }

    inner class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Restaurant?) {
            binding.movie = item
            binding.cardMovie.setOnClickListener {
                listener.onSelectMovie(item)
            }
            binding.executePendingBindings()
        }
    }


    interface OnAdapterInteraction {
        fun onSelectMovie(restaurant: Restaurant?)
    }
}