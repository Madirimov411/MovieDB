package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R

class PopularAdapter(val list: ArrayList<com.uzb7.moviedb.model.Result>):RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {



    fun submitList(newList: ArrayList<com.uzb7.moviedb.model.Result>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    class PopularViewHolder(view: View):RecyclerView.ViewHolder(view){
        val movieImage=view.findViewById<ImageView>(R.id.ivMovieHome)
        val movieName=view.findViewById<TextView>(R.id.tvMovieNameHome)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_popular,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular=list[position]
        holder.apply {
            Glide.with(movieImage).load("https://image.tmdb.org/t/p/w500${popular.poster_path}").placeholder(R.drawable.loading).into(movieImage)
            movieName.text=popular.original_title
        }
    }

}