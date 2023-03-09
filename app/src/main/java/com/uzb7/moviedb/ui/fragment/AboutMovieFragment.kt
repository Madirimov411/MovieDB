package com.uzb7.moviedb.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
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
import com.uzb7.moviedb.utils.Extension.show
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AboutMovieFragment : Fragment(R.layout.fragment_about_movie) {
    lateinit var aboutMovie: AboutMovie
    lateinit var list: ArrayList<Result>
    var myId: Int=0
    lateinit var listSimilar: ArrayList<com.uzb7.moviedb.model.similar_movie.Result>
    lateinit var listCast: ArrayList<Cast>
    private val binding by viewBinding { FragmentAboutMovieBinding.bind(it) }
    private val args: AboutMovieFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initViews() {
        binding.apply {
            if(isInternetAviable()){
                putAbout()
            }
            else{
                frameAbout.hide()
                animationViewLoading.hide()
                animationViewNoInternet.show()
                tvNoInternet.show()
            }
            refreshLayout.setOnRefreshListener {
                refreshLayout.isRefreshing = false
                if (isInternetAviable()) {
                    putAbout()
                } else {
                    frameAbout.hide()
                    animationViewLoading.hide()
                    animationViewNoInternet.show()
                    tvNoInternet.show()

                }
            }

        }
    }

    private fun putAbout(){
        myId = args.id
        val which = args.which
        val type = args.type
        binding.apply {
            animationViewNoInternet.hide()
            tvNoInternet.hide()
            frameAbout.show()
            loadMovie(myId)
            ivBack.setOnClickListener {
                //requireActivity().onBackPressed()
                if (which == 1) {
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_homeFragment)
                } else if (which == 2) {
                    var bundle = Bundle()
                    bundle.putString("movieType", "$type")
                    findNavController().navigate(
                        R.id.action_aboutMovieFragment_to_allMovieFragment,
                        bundle
                    )
                } else if (which == 3) {
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_searchFragment)
                }
            }
            llReview.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", myId)
                bundle.putInt("which", which)
                findNavController().navigate(
                    R.id.action_aboutMovieFragment_to_reviewFragment,
                    bundle
                )
            }
            requireActivity().onBackPressedDispatcher.addCallback(this@AboutMovieFragment) {
                if (which == 1) {
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_homeFragment)
                } else if (which == 2) {
                    var bundle = Bundle()
                    bundle.putString("movieType", "$type")
                    findNavController().navigate(
                        R.id.action_aboutMovieFragment_to_allMovieFragment,
                        bundle
                    )
                } else if (which == 3) {
                    findNavController().navigate(R.id.action_aboutMovieFragment_to_searchFragment)
                }
            }
        }

    }

    private fun loadMovie(id: Int) {
        binding.animationViewLoading.show()
        binding.llAboutLinear.hide()
        ApiClient.apiService.getMovieById(id).enqueue(object : Callback<AboutMovie> {
            override fun onResponse(call: Call<AboutMovie>, response: Response<AboutMovie>) {
                if(response.isSuccessful){
                    aboutMovie = response.body()!!
                    setAboutMovie(id)
                    binding.animationViewLoading.hide()
                    binding.llAboutLinear.show()
                }
            }
            override fun onFailure(call: Call<AboutMovie>, t: Throwable) {

            }

        })
    }


    @SuppressLint("SetTextI18n")
    private fun setAboutMovie(id: Int) {
        binding.apply {
            tvMovieNameAbout.text = aboutMovie.title
            if (aboutMovie.poster_path == null) ivMovieAbout.hide()
            else Glide.with(ivMovieAbout)
                .load(CreateUrl.imageOpen(aboutMovie.poster_path)).into(ivMovieAbout)
            movieGenre.text = loadGenre()
            tvMovieRatio.text = aboutMovie.vote_average.toString()[0]
                .toString() + aboutMovie.vote_average.toString()[1]
                .toString() + aboutMovie.vote_average.toString()[2].toString()
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
                    myId=it
                    loadMovie(myId)
                }
            }
        }
    }


    private fun loadMovieComposition(id: Int) {
        ApiClient.apiService.getMovieComposition(id).enqueue(object : Callback<Actors> {
            override fun onResponse(call: Call<Actors>, response: Response<Actors>) {
                if (response.isSuccessful) {
                    listCast = response.body()!!.cast
                    loadCast()
                }
            }

            override fun onFailure(call: Call<Actors>, t: Throwable) {
            }

        })
    }

    private fun loadCast() {
        binding.apply {
            val adapter = TopCastAdapter(listCast)
            rvTopBilledCast.adapter = adapter
            rvTopBilledCast.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun loadTrailer() {
        binding.apply {
            list = aboutMovie.videos.results

            if (list.size != 0) {
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
                tvNoTrailers.show()
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
        var a = ""
        s += aboutMovie.release_date.split("-")[0] + ", "
        for (i in 0 until aboutMovie.production_countries.size) {
            s += aboutMovie.production_countries[i].iso_3166_1 + ", "
        }
        for (i in 0 until aboutMovie.genres.size) {
            s += aboutMovie.genres[i].name + ", "
        }
        for (i in 0 until s.length - 2) a += s[i]
        return a
    }

    private fun isInternetAviable(): Boolean {
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}
