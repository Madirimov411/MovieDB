package com.uzb7.moviedb.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.DetailMovieAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentAboutMovieBinding
import com.uzb7.moviedb.model.youtube_videos.AboutMovie
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AboutMovieFragment : Fragment(R.layout.fragment_about_movie) {
    lateinit var aboutMovie: AboutMovie
    lateinit var list: ArrayList<com.uzb7.moviedb.model.youtube_videos.Result>
    private val binding by viewBinding { FragmentAboutMovieBinding.bind(it) }
    private val args: AboutMovieFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        val id = args.id
        binding.apply {
            loadMovie(id)


        }
    }


    private fun loadMovie(id: Int) {
        ApiClient.apiService.getMovieById(id).enqueue(object : Callback<AboutMovie> {
            override fun onResponse(call: Call<AboutMovie>, response: Response<AboutMovie>) {
                aboutMovie = response.body()!!
                setAboutMovie()
            }

            override fun onFailure(call: Call<AboutMovie>, t: Throwable) {

            }

        })
    }

    private fun setAboutMovie() {
        binding.apply {
            tvMovieNameAbout.text = aboutMovie.title
            movieGenre.text = loadGenre()
            tvMovieRatio.text = aboutMovie.vote_average.toString()
            tvMovieLanguage.text = aboutMovie.original_language.uppercase()
            tvMovieStatus.text = aboutMovie.status
            tvMovieRevenu.text = loadRevenu(aboutMovie.revenue)
            tvoriginalTitle.text = aboutMovie.original_title
            tvMovieOverview.text = aboutMovie.tagline
            tvmovieAbout.text = aboutMovie.overview
            list=aboutMovie.videos.results
            val adapter=DetailMovieAdapter(list)
            rvTrailer.adapter=adapter
            rvTrailer.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter.trailer={loadTrailer(it)}
        }
    }

    private fun loadTrailer(trailer: String) {
        val implicit = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$trailer"))
        startActivity(implicit)
    }
    private fun loadRevenu(revenue: Int): String {
        var s = ""
        var a = ""
        var r = revenue.toString()
        for (i in r.length - 1 downTo 0) {
            s = r[i] + s
            if (i % 3 == 0) {
                s = ",$s"
            }
        }
        if (s[0] == ',') {
            for (i in 1 until s.length) a += s[i]
            return a
        }
        return s
    }
    private fun loadGenre(): String {
        var s = ""
        s += aboutMovie.release_date.split("-")[0] + ", "
        for (i in 0 until aboutMovie.production_countries.size) {
            s += aboutMovie.production_countries[i].iso_3166_1 + ", "
        }
        for (i in 0 until aboutMovie.genres.size) {
            if (i == aboutMovie.genres.size - 1) {
                s += aboutMovie.genres[i].name
            } else {
                s += aboutMovie.genres[i].name + ", "
            }
        }
        return s
    }
}