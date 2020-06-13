package com.manubla.cinemapp.presentation.view.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse
import com.manubla.cinemapp.databinding.FragmentReviewBinding
import com.manubla.cinemapp.presentation.util.showLongErrorMessage
import com.manubla.cinemapp.presentation.view.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewFragment: Fragment() {

    private lateinit var adapter: ReviewAdapter
    private val viewModel: ReviewViewModel by viewModel()

    private var currentPage = 1
    private var loading = true
    private var online = true

    private lateinit var binding: FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_review,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie?>(DetailActivity.MovieKey)

        if (movie == null)
            activity?.finish()
        else {
            binding.movie = movie
            adapter = ReviewAdapter()
            viewModel.reviews.observe(this, Observer(this::reviewsChanged))
            val layoutManager = LinearLayoutManager(activity)
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
                                    viewModel.loadReviews(movie.id, currentPage)
                                }
                            }
                        }
                    }
                })
            }

            swipeLayout.isRefreshing = true
            viewModel.loadReviews(movie.id, currentPage)

            swipeLayout.setOnRefreshListener {
                adapter.reviews = arrayListOf()
                currentPage = 1
                viewModel.loadReviews(movie.id, currentPage)
            }
        }
    }


    private fun reviewsChanged(page: ReviewsPageResponse) {
        if (currentPage > 1)
            adapter.removeProgressItem()

        if (page.fromCloud) {
            if (!online) {
                online = true
                adapter.reviews = arrayListOf()
            }
        } else {
            if (online || swipeLayout.isRefreshing) {
                online = false
                showLongErrorMessage(getString(R.string.no_network_connection), view, context)
                adapter.reviews = arrayListOf()
            }
        }

        swipeLayout.isRefreshing = false
        adapter.addMovieItems(page.results)
        if (currentPage == 1)
            recyclerView.scheduleLayoutAnimation()

        currentPage++
        loading = false
    }

}
