package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.utils.CreateUrl
import com.uzb7.moviedb.utils.Extension.hide

class SimilarMovieAdapter(val list: ArrayList<com.uzb7.moviedb.model.similar_movie.Result>):RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder>() {

    var about:((Int)->Unit)?=null

    class SimilarMovieViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image=view.findViewById<ImageView>(R.id.ivSimilerMovies)
        val name=view.findViewById<TextView>(R.id.tvSimilarMovieName)
        val detail=view.findViewById<LinearLayout>(R.id.llAbout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_similer_movies,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val similar=list[position]
        holder.apply {
            if(similar.poster_path==null) image.hide()
             else Glide.with(image).load(CreateUrl.imageOpen(similar.poster_path)).into(image)
            name.text=similar.title
            detail.setOnClickListener {
                about?.invoke(similar.id)
            }
        }
    }
}