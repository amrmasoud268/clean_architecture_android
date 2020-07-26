package com.example.cleanarch.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarch.R
import com.example.cleanarch.core.utils.load
import com.example.cleanarch.data.model.Photo
import kotlinx.android.synthetic.main.item_movie_photo.view.*

class PhotosAdapter(var photos: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.ivPhoto.load(photos[position].url)
    }

    fun updateList(photos: List<Photo>) {
        this.photos = photos.toMutableList()
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.ivPhoto
    }
}