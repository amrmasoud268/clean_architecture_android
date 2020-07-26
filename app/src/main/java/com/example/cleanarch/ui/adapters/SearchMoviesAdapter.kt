package com.example.cleanarch.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarch.R
import com.example.cleanarch.data.model.Movie
import kotlinx.android.synthetic.main.item_search_movies.view.*

class SearchMoviesAdapter(val activity: Activity) :
    RecyclerView.Adapter<SearchMoviesAdapter.SearchedMoviesViewHolder>() {

    private var moviesMap = mutableMapOf<Int, List<Movie>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_movies, parent, false)
        return SearchedMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesMap.size
    }

    override fun onBindViewHolder(holder: SearchedMoviesViewHolder, position: Int) {
        holder.tvYear.text = moviesMap.keys.elementAt(position).toString()
        with(holder.rvMovies) {
            adapter = MoviesAdapter(activity, twoPane = false)
            (adapter as MoviesAdapter).updateMoviesAdapter(moviesMap.values.elementAt(position))
        }
    }

    fun updateMap(moviesMap: MutableMap<Int, List<Movie>>) {
        this.moviesMap = moviesMap
        notifyDataSetChanged()
    }

    inner class SearchedMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvMovies: RecyclerView = view.rvSearchedMovies
        val tvYear: TextView = view.tvYear
    }
}