package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.model.Popular

class PopularAdapter(val list: ArrayList<Popular>):RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    class PopularViewHolder(view: View):RecyclerView.ViewHolder(view){
        val movieImage=view.findViewById<ImageView>(R.id.ivMovie)
        val movieName=view.findViewById<TextView>(R.id.tvMovieName)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_popular,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular=list[position]
        holder.apply {
            Glide.with(movieImage).load("https://image.tmdb.org/t/p/w500${popular.results[0].poster_path}").into(movieImage)
            movieName.text=popular.results[0].original_title
        }
    }

}