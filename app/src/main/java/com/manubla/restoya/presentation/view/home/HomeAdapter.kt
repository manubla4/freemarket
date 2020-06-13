package com.manubla.restoya.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.restoya.R
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.databinding.ItemMovieBinding

class HomeAdapter(private val listener: OnAdapterInteraction):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var movies = arrayListOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addMovieItems(items: List<Restaurant>) {
        movies.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Any) {
        movies.add(item)
        notifyDataSetChanged()
    }

    fun removeProgressItem() {
        if (movies.isNotEmpty() && movies[movies.size-1] !is Restaurant)
            movies.removeAt(movies.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == MOVIE_TYPE) {
            MovieViewHolder(ItemMovieBinding.inflate(inflater))
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (movies[position] is Restaurant)
            return MOVIE_TYPE
        return PROGRESS_TYPE
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

    inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val PROGRESS_TYPE = 1
        private const val MOVIE_TYPE = 2
    }

    interface OnAdapterInteraction {
        fun onSelectMovie(restaurant: Restaurant?)
    }
}