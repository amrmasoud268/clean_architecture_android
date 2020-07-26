package com.example.cleanarch.ui.activities

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.example.cleanarch.R
import com.example.cleanarch.core.base.BaseActivity
import com.example.cleanarch.ui.fragments.MoviesFragment
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : BaseActivity() {

    private var twoPane: Boolean = false

    override fun getLayout(): Int = R.layout.activity_movies

    override fun init() {
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (movieDetailContainer != null) {
            twoPane = true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.containerMovies, MoviesFragment.newInstance(twoPane))
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startActivity(Intent(this, SearchMoviesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}