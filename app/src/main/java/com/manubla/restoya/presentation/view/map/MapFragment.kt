package com.manubla.restoya.presentation.view.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manubla.restoya.R

class MapFragment : Fragment() {
//    private val viewModel: MapViewModel by viewModel()
//    private lateinit var adapter: MapAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        adapter = MapAdapter(this)
//        val layoutManager = GridLayoutManager(activity, 2)
//        recyclerView.let {
//            it.layoutManager = layoutManager
//            it.adapter = adapter
//        }
//
//        viewModel.movies.observe(this, Observer(this::moviesChanged))
//
//        swipeLayout.setOnRefreshListener {
//            viewModel.loadMovies()
//        }
    }

    override fun onResume() {
        super.onResume()
//        viewModel.loadMovies()
    }

//    private fun moviesChanged(restaurants: List<Restaurant>) {
//        swipeLayout.isRefreshing = false
//        adapter.movies = restaurants
//        recyclerView.scheduleLayoutAnimation()
//    }

//    override fun onSelectMovie(restaurant: Restaurant?) {
//        val intent = Intent(activity, DetailActivity::class.java)
//        intent.putExtra(DetailActivity.MovieKey, restaurant)
//        startActivity(intent)
//    }

    companion object {

        @JvmStatic
        fun newInstance() = MapFragment()
    }
}
