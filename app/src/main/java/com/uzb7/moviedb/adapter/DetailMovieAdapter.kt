package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.utils.CreateUrl

class DetailMovieAdapter(val list: ArrayList<com.uzb7.moviedb.model.youtube_videos.Result>) :
    RecyclerView.Adapter<DetailMovieAdapter.DetailMovieViewHolder>() {

    var trailer: ((String) -> Unit)? = null

    class DetailMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image=view.findViewById<ImageView>(R.id.ivMovieTrailer)
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

            Glide.with(image).load(CreateUrl.youtubeImage(detail.key)).into(image)
            youtubeTrailer.setOnClickListener {
                trailer?.invoke(detail.key)
            }
        }
    }

}