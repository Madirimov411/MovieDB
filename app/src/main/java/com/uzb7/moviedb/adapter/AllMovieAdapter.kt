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

class AllMovieAdapter(private val list: ArrayList<Result>):RecyclerView.Adapter<AllMovieAdapter.AllMovieViewHolder>() {

    var detail:((Int)->Unit)?=null
    fun submitList(newList: ArrayList<Result>){
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class AllMovieViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image: ImageView =view.findViewById(R.id.ivMovieAll)
        val name: TextView =view.findViewById(R.id.tvMovieNameAll)
        val detailMovie=view.findViewById<LinearLayout>(R.id.llAboutAll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMovieViewHolder {
        return AllMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_all_type,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AllMovieViewHolder, position: Int) {
        val allType=list[position]
        holder.apply {
            Glide.with(image).load(CreateUrl.imageOpen(allType.poster_path)).placeholder(R.drawable.loading).into(image)
            name.text=allType.original_title
            detailMovie.setOnClickListener{
                detail?.invoke(allType.id)
            }
        }
    }
}