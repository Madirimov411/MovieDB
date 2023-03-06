package com.uzb7.moviedb.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.DetailMovieAdapter
import com.uzb7.moviedb.adapter.SimilarMovieAdapter
import com.uzb7.moviedb.adapter.TopCastAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentAboutMovieBinding
import com.uzb7.moviedb.model.actors.Actors
import com.uzb7.moviedb.model.actors.Cast
import com.uzb7.moviedb.model.similar_movie.Similar
import com.uzb7.moviedb.model.youtube_videos.AboutMovie
import com.uzb7.moviedb.model.youtube_videos.Result
import com.uzb7.moviedb.utils.CreateUrl
import com.uzb7.moviedb.utils.Extension.hide
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AboutMovieFragment : Fragment(R.layout.fragment_about_movie) {
    lateinit var aboutMovie: AboutMovie
    lateinit var list: ArrayList<Result>
    lateinit var listSimilar: ArrayList<com.uzb7.moviedb.model.similar_movie.Result>
    lateinit var listCast: ArrayList<Cast>
    private val binding by viewBinding { FragmentAboutMovieBinding.bind(it) }
    private val args: AboutMovieFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }




    private fun initViews() {
        val id = args.id
        val which = args.which
        val type=args.type
        binding.apply {
            loadMovie(id)
            ivBack.setOnClickListener {
                if (which == 1) {
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_homeFragment)
                } else if(which==2) {
                    var bundle=Bundle()
                    bundle.putString("movieType","$type")
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_allMovieFragment,bundle)
                }
                else{
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_searchFragment)
                }
            }



        }
    }


    override fun onDetach() {
        super.onDetach()
    }


    private fun loadMovie(id: Int) {
        ApiClient.apiService.getMovieById(id).enqueue(object : Callback<AboutMovie> {
            override fun onResponse(call: Call<AboutMovie>, response: Response<AboutMovie>) {
                aboutMovie = response.body()!!
                setAboutMovie(id)
            }

            override fun onFailure(call: Call<AboutMovie>, t: Throwable) {

            }

        })
    }


    private fun setAboutMovie(id:Int) {
        binding.apply {
            tvMovieNameAbout.text = aboutMovie.title
            Glide.with(ivMovieAbout).load(CreateUrl.imageOpen(aboutMovie.poster_path))
                .into(ivMovieAbout)
            movieGenre.text = loadGenre()
            tvMovieRatio.text = aboutMovie.vote_average.toString().get(0)
                .toString() + aboutMovie.vote_average.toString().get(1)
                .toString() + aboutMovie.vote_average.toString().get(2).toString()
            tvMovieLanguage.text = aboutMovie.original_language.uppercase()
            tvMovieStatus.text = aboutMovie.status
            tvMovieRevenu.text = loadRevenu(aboutMovie.revenue)
            tvoriginalTitle.text = aboutMovie.original_title
            tvMovieOverview.text = aboutMovie.tagline
            tvmovieAbout.text = aboutMovie.overview
            loadTrailer()
            loadSimilar(aboutMovie.id)
            loadMovieComposition(id)

        }
    }

    private fun loadSimilar(id: Int) {
        ApiClient.apiService.getSimilarMovie(id = id, page = 1).enqueue(object : Callback<Similar> {
            override fun onResponse(call: Call<Similar>, response: Response<Similar>) {
                if (response.isSuccessful) {
                    listSimilar = response.body()!!.results
                    Log.d("@@@@", response.body()!!.toString())
                    loadSimilarRV()
                }
            }

            override fun onFailure(call: Call<Similar>, t: Throwable) {
            }

        })
    }

    private fun loadSimilarRV() {
        binding.apply {
            if (listSimilar != null) {
                val adapter = SimilarMovieAdapter(listSimilar)
                rvSimilar.adapter = adapter
                rvSimilar.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter.about = {
                    loadMovie(it)
                }
            }
        }
    }

    private fun loadMovieComposition(id:Int){
        ApiClient.apiService.getMovieComposition(id).enqueue(object :Callback<Actors>{
            override fun onResponse(call: Call<Actors>, response: Response<Actors>) {
                if (response.isSuccessful){
                    listCast=response.body()!!.cast
                    loadCast()
                }
            }

            override fun onFailure(call: Call<Actors>, t: Throwable) {
            }

        })
    }

    private fun loadCast() {
        binding.apply{
            val adapter = TopCastAdapter(listCast)
            rvTopBilledCast.adapter=adapter
            rvTopBilledCast.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun loadTrailer() {
        binding.apply {
            list = aboutMovie.videos.results

            if (list != null) {
                tvNoTrailers.hide()
                val adapter = DetailMovieAdapter(list)
                rvTrailer.adapter = adapter
                rvTrailer.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter.trailer = {
                    val implicit =
                        Intent(Intent.ACTION_VIEW, Uri.parse(CreateUrl.youtubeOpen(it)))
                    startActivity(implicit)
                }
            } else {
                rvTrailer.hide()
            }

        }
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