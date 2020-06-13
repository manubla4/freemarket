package com.manubla.cinemapp.presentation.view.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.response.ConfigurationResponse
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import com.manubla.cinemapp.presentation.view.favorites.FavoritesFragment
import kotlinx.android.synthetic.main.activity_home.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        if (savedInstanceState == null) {
            val movie = intent.getParcelableExtra<Movie?>(MovieKey)
            val reviewFragment = ReviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MovieKey, movie)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, reviewFragment, "ReviewFragmentTag")
                .commit()

        }
    }

    companion object {
        const val MovieKey = "MovieKey"
    }
}