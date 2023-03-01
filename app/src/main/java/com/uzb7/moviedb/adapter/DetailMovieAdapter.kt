package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.uzb7.moviedb.R

class DetailMovieAdapter(val list: ArrayList<com.uzb7.moviedb.model.youtube_videos.Result>) :
    RecyclerView.Adapter<DetailMovieAdapter.DetailMovieViewHolder>() {

    var trailer: ((String) -> Unit)? = null

    class DetailMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val youtubeTrailer = view.findViewById<FrameLayout>(R.id.youtubeTrailer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMovieViewHolder {
        return DetailMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trailler, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DetailMovieViewHolder, position: Int) {
        val detail=list[position]
        holder.apply {
            youtubeTrailer.setOnClickListener {
                trailer?.invoke(detail.key)
            }
        }
    }

}