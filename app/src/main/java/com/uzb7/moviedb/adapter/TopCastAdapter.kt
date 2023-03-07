package com.uzb7.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.model.actors.Cast
import com.uzb7.moviedb.utils.CreateUrl

class TopCastAdapter(val list: ArrayList<Cast>) :
    RecyclerView.Adapter<TopCastAdapter.TopCastViewHolder>() {

    class TopCastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivActors = view.findViewById<ImageView>(R.id.ivActors)
        val name = view.findViewById<TextView>(R.id.tvActorName)
        val role = view.findViewById<TextView>(R.id.tvAcrorMovieName)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCastViewHolder {
        return TopCastViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_top_billed_cast, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TopCastViewHolder, position: Int) {
        val cast = list[position]
        holder.apply {
            if (cast.profile_path != null) {
                Glide.with(ivActors).load(CreateUrl.imageOpen(cast.profile_path))
                    .placeholder(R.drawable.loading).into(ivActors)
            }
            name.text = cast.name
            role.text = cast.character
        }
    }
}