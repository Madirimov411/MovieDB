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

class ReviewAdapter(val list: ArrayList<com.uzb7.moviedb.model.review.Result>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    fun submitList(newList: ArrayList<com.uzb7.moviedb.model.review.Result>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfile = view.findViewById<ImageView>(R.id.ivProfile)
        val tvName = view.findViewById<TextView>(R.id.tvProfileName)
        val name = view.findViewById<TextView>(R.id.profileName)
        val desc = view.findViewById<TextView>(R.id.tvComment)
        val time = view.findViewById<TextView>(R.id.tvTime)
        val imageNull = view.findViewById<TextView>(R.id.imageNull)
        val rating=view.findViewById<TextView>(R.id.movieRatio)
        val llRating=view.findViewById<LinearLayout>(R.id.llRating)
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
            val avatar = review.author_details.avatar_path
            if (avatar != null && avatar[0].toString() == "/") {
                imageNull.hide()
                Glide.with(ivProfile).load(imageOpen(avatar))
                    .into(ivProfile)
            } else if (review.author_details.avatar_path != null) {
                imageNull.hide()
                Glide.with(ivProfile).load(CreateUrl.imageOpen(review.author_details.avatar_path))
                    .into(ivProfile)

            } else {
                imageNull.text = review.author[0].toString()
            }
            if (review.author_details.rating==null||review.author_details.rating.toString()=="0.0") llRating.hide()
            else rating.text=review.author_details.rating.toString()
            tvName.text = review.author
            name.text = review.author_details.username
            desc.text = review.content
            time.text = " ${time(review.updated_at)}"
        }
    }

    private fun imageOpen(avatarPath: String): String {
        var a = ""
        for (i in 1 until avatarPath.length) {
            a += avatarPath[i].toString()
        }
        return a
    }

    private fun time(time: String): String {
        val mont=time[5].toString() + time[6].toString()
        return "${months(mont)} ${time[8].toString() + time[9].toString()}, ${time[0].toString() + time[1].toString() + time[2].toString() + time[3].toString()}"
    }

    private fun months(s: String): String {
        when (s) {
            "01" -> return "Jan"
            "02" -> return "Feb"
            "03" -> return "Mar"
            "04" -> return "Apr"
            "05" -> return "May"
            "06" -> return "June"
            "07" -> return "July"
            "08" -> return "Aug"
            "09" -> return "Sep"
            "10" -> return "Oct"
            "11" -> return "Nov"
            "12" -> return "Dec"
            else -> return ""
        }
    }
}