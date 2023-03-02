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
import com.uzb7.moviedb.model.Result
import com.uzb7.moviedb.utils.CreateUrl

class UpcomingAdapter(val list: ArrayList<Result>):RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {
    var detail: ((Int) -> Unit)? = null
    fun submitList(newList: ArrayList<Result>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    class UpcomingViewHolder(view: View): RecyclerView.ViewHolder(view){
        val movieImage=view.findViewById<ImageView>(R.id.ivMovieHome)
        val movieName=view.findViewById<TextView>(R.id.tvMovieNameHome)
        val detailmovie = view.findViewById<LinearLayout>(R.id.llDetail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_popular,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val popular=list[position]
        holder.apply {
            Glide.with(movieImage).load(CreateUrl.imageOpen(popular.poster_path)).placeholder(
                R.drawable.loading).into(movieImage)
            movieName.text=popular.original_title
            detailmovie.setOnClickListener {
                detail?.invoke(popular.id)
            }
        }
    }
}