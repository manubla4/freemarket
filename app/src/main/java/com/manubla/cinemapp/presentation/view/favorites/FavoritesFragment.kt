package com.manubla.cinemapp.presentation.view.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.presentation.view.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), FavoritesAdapter.OnAdapterInteraction {
    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritesAdapter(this)
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }

        viewModel.movies.observe(this, Observer(this::moviesChanged))

        swipeLayout.setOnRefreshListener {
            viewModel.loadMovies()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMovies()
    }

    private fun moviesChanged(movies: List<Movie>) {
        swipeLayout.isRefreshing = false
        adapter.movies = movies
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onSelectMovie(movie: Movie?) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MovieKey, movie)
        startActivity(intent)
    }
}
