package com.manubla.cinemapp.presentation.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Movie

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val movie = intent.getParcelableExtra<Movie?>(MovieKey)
            val detailFragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MovieKey, movie)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, detailFragment, "DetailFragmentTag")
                .commit()

        }
    }

    companion object {
        const val MovieKey = "MovieKey"
    }

}