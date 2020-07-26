package com.example.cleanarch.ui.adapters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarch.R
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.ui.activities.MovieDetailActivity
import com.example.cleanarch.ui.fragments.MovieDetailFragment

class MoviesAdapter(
    private val activity: Activity,
    private var movies: List<Movie> = mutableListOf(),
    private val twoPane: Boolean
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        movies = mutableListOf()
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Movie
            if (twoPane) {
                val fragment = MovieDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(MovieDetailFragment.MOVIE_ITEM, item)
                    }
                }
                (activity as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.movieDetailContainer, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailFragment.MOVIE_ITEM, item)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_content, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.idView.text = movie.title
        holder.contentView.text = movie.year.toString()

        with(holder.itemView) {
            tag = movie
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = movies.size

    fun updateMoviesAdapter(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.id_text)
        val contentView: TextView = view.findViewById(R.id.content)
    }
}