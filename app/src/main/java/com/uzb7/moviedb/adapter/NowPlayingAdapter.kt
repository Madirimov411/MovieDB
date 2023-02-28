package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.utils.CreateUrl

class NowPlayingAdapter(val list:ArrayList<com.uzb7.moviedb.model.Result>):RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {

    fun submitlist(newList:ArrayList<com.uzb7.moviedb.model.Result>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()

    }

    class NowPlayingViewHolder(view:View):RecyclerView.ViewHolder(view){
        val imageBack=view.findViewById<ImageView>(R.id.ivMovieImage)
        val imagePoster=view.findViewById<ImageView>(R.id.ivMovieImage2)
        val title=view.findViewById<TextView>(R.id.tvMovieNameNowPlaying)
        val desc=view.findViewById<TextView>(R.id.tvMovieDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        return NowPlayingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val now=list[position]
        holder.apply {
            Glide.with(imageBack).load(CreateUrl.imageOpen(now.backdrop_path)).into(imageBack)
            Glide.with(imagePoster).load(CreateUrl.imageOpen(now.poster_path)).into(imagePoster)
            title.text=now.title
            desc.text=now.overview
        }
    }

}