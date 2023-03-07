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

class ReviewAdapter(val list: ArrayList<com.uzb7.moviedb.model.review.Result>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {


    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfile = view.findViewById<ImageView>(R.id.ivProfile)
        val tvName = view.findViewById<TextView>(R.id.tvProfileName)
        val name = view.findViewById<TextView>(R.id.profileName)
        val desc = view.findViewById<TextView>(R.id.tvComment)
        val time = view.findViewById<TextView>(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = list[position]
        holder.apply {
            Glide.with(ivProfile).load(CreateUrl.imageOpen(review.author_details.avatar_path))
                .into(ivProfile)
            tvName.text = review.author
            name.text = review.author_details.username
            desc.text = review.content
            time.text = " ${time(review.updated_at)}"
        }
    }

    private fun time(time: String): String {
        return "${months(time[5].toString() + time[6].toString())} ${time[8].toString() + time[9].toString()}, ${time[0].toString() + time[1].toString() + time[2].toString() + time[3].toString()}"
    }

    private fun months(s: String): String {
        when (s) {
            "1" -> return "Jan"
            "2" -> return "Feb"
            "3" -> return "Mar"
            "4" -> return "Apr"
            "5" -> return "May"
            "6" -> return "June"
            "7" -> return "July"
            "8" -> return "Aug"
            "9" -> return "Sep"
            "10" -> return "Oct"
            "11" -> return "Nov"
            "12" -> return "Dec"
            else -> return ""
        }
    }
}