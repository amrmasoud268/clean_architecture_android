package com.example.cleanarch.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanarch.R
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.ui.fragments.MovieDetailFragment
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        MovieDetailFragment.MOVIE_ITEM,
                        intent.getParcelableExtra<Movie>(MovieDetailFragment.MOVIE_ITEM)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.movieDetailContainer, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}