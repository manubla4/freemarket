package com.manubla.cinemapp.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import com.manubla.cinemapp.presentation.helper.editTextString
import com.manubla.cinemapp.presentation.helper.gone
import com.manubla.cinemapp.presentation.helper.visible
import com.manubla.cinemapp.presentation.util.showLongErrorMessage
import com.manubla.cinemapp.presentation.view.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(), HomeAdapter.OnAdapterInteraction {

    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModel()

    private var currentRating = 0f
    private var currentPage = 1
    private var loading = true
    private var online = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainLayout.requestFocus()
        adapter = HomeAdapter(this)
        viewModel.data.observe(this, Observer(this::dataChanged))
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                        if (!loading) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                loading = true
                                adapter.addItem(Any())  //Progress item
                                viewModel.loadData(currentPage)
                            }
                        }
                    }
                }
            })
        }


        ratingBar.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                ratingBar.setIsIndicator(false)
            if (event.action == MotionEvent.ACTION_UP) {
                if (ratingBar.rating == currentRating) {
                    currentRating = 0f
                    ratingBar.rating = currentRating
                    ratingBar.setIsIndicator(true)
                    doRefresh()
                } else {
                    currentRating = ratingBar.rating
                    doFilter()
                }
            }
            false
        }

        swipeLayout.isRefreshing = true
        viewModel.loadData(currentPage)


        swipeLayout.setOnRefreshListener {
            doRefresh()
        }

        editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                doSearch()
            false
        }
        editSearch.doAfterTextChanged {
            if (editSearch.editTextString().isEmpty())
                doRefresh()
            else
                doSearch()
        }
    }

    private fun doRefresh() {
        swipeLayout.isRefreshing = true
        adapter.movies = arrayListOf()
        currentPage = 1
        viewModel.loadData(currentPage)
    }

    private fun doSearch() {
        swipeLayout.isRefreshing = true
        adapter.movies = arrayListOf()
        currentPage = 1
        viewModel.searchMovies(editSearch.editTextString(), currentPage)
    }

    private fun doFilter() {
        swipeLayout.isRefreshing = true
        var min = 0.0
        var max = 10.0
        when {
            currentRating.toInt() == 1 -> {
                min = 0.0
                max = 2.0
            }
            currentRating.toInt() == 2 -> {
                min = 2.0
                max = 4.0
            }
            currentRating.toInt() == 3 -> {
                min = 4.0
                max = 6.0
            }
            currentRating.toInt() == 4 -> {
                min = 6.0
                max = 8.0
            }
            currentRating.toInt() == 5 -> {
                min = 8.0
                max = 10.0
            }
        }

        adapter.movies = arrayListOf()
        currentPage = 1
        viewModel.loadFilteredData(currentPage, min, max)
    }


    private fun dataChanged(page: MoviesPageResponse) {
        if (currentPage > 1)
            adapter.removeProgressItem()

        if (page.fromCloud) {
            if (!online) {
                online = true
                editSearch.visible()
                adapter.movies = arrayListOf()
            }
        } else {
            if (online || swipeLayout.isRefreshing) {
                online = false
                editSearch.gone()
                showLongErrorMessage(getString(R.string.no_network_connection), view, context)
                adapter.movies = arrayListOf()
            }
        }

        swipeLayout.isRefreshing = false
        adapter.addMovieItems(page.results)
        if (currentPage == 1)
            recyclerView.scheduleLayoutAnimation()

        currentPage++
        loading = false
    }

    override fun onSelectMovie(movie: Movie?) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MovieKey, movie)
        startActivity(intent)
    }
}
