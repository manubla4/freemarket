package com.manubla.cinemapp.presentation.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.databinding.FragmentDetailBinding
import com.manubla.cinemapp.presentation.view.review.ReviewActivity
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.ZonedDateTime

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentDetailBinding
    private var favoriteDate: ZonedDateTime? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie?>(DetailActivity.MovieKey)
        if (movie != null) {
            btnReviews.setOnClickListener {
                val intent = Intent(activity, ReviewActivity::class.java)
                intent.putExtra(ReviewActivity.MovieKey, movie)
                startActivity(intent)
            }
            btnFavorite.setOnClickListener {
                viewModel.updateFavorite(movie.id, favoriteDate)
            }

            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
            binding.movie = movie

            viewModel.favoriteDate.observe(this, Observer(this::favoriteChanged))
            viewModel.loadFavorite(movie.id)
            viewModel.loadGenres(movie.id)
        }
        else
            activity?.finish()
    }

    private fun favoriteChanged(date: ZonedDateTime?) {
        favoriteDate = date
    }


}
