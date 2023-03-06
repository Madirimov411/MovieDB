package com.uzb7.moviedb.ui.fragment

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.NowPlayingAdapter
import com.uzb7.moviedb.adapter.PopularAdapter
import com.uzb7.moviedb.adapter.TopRatedAdapter
import com.uzb7.moviedb.adapter.UpcomingAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentHomeBinding
import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.model.Result
import com.uzb7.moviedb.utils.Extension.hide
import com.uzb7.moviedb.utils.Extension.show
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    var listPopular = ArrayList<Result>()
    var listTopRated = ArrayList<Result>()
    var listUpcoming = ArrayList<Result>()
    var listNowPlaying = ArrayList<Result>()
    lateinit var adapterPopular: PopularAdapter
    lateinit var adapterTopRated: TopRatedAdapter
    lateinit var adapterUpcoming: UpcomingAdapter
    lateinit var adapterNowPlaying: NowPlayingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {



            if (isInternetAviable()) {
                tvNoInternet.hide()
                loadPopular()
                loadTopRated()
                loadUpcoming()
                loadNowPlaying()
                rvPopularRefresh(listPopular)
                rvTopRatedRefresh(listTopRated)
                rvUpcomingRefresh(listUpcoming)
                rvNowPlayingRefresh(listNowPlaying)

                ivSearch.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }

                tvPopularAll.setOnClickListener {
                    val bundle = Bundle()
                    val type = "popular"
                    bundle.putString("movieType", type)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_allMovieFragment,
                        bundle
                    )
                }
                tvTopAll.setOnClickListener {
                    val bundle = Bundle()
                    val type = "top_rated"
                    bundle.putString("movieType", type)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_allMovieFragment,
                        bundle
                    )

                }
                tvUpcomingAll.setOnClickListener {
                    val bundle = Bundle()
                    val type = "upcoming"
                    bundle.putString("movieType", type)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_allMovieFragment,
                        bundle
                    )
                }
            }
            else{
                llHome.hide()
                tvNoInternet.show()
            }

        }
    }

    private fun rvUpcomingRefresh(listUpcoming: java.util.ArrayList<Result>) {
        binding.apply {
            adapterUpcoming = UpcomingAdapter(listUpcoming)
            rvUpcoming.adapter = adapterUpcoming
            rvUpcoming.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterUpcoming.detail = {
                val bundle = Bundle()
                bundle.putInt("id", it)
                bundle.putInt("which", 1)
                findNavController().navigate(R.id.action_homeFragment_to_aboutMovieFragment, bundle)
            }
        }
    }

    private fun rvTopRatedRefresh(listTopRated: java.util.ArrayList<Result>) {
        binding.apply {
            adapterTopRated = TopRatedAdapter(listTopRated)
            rvTopRated.adapter = adapterTopRated
            rvTopRated.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterTopRated.detail = {
                val bundle = Bundle()
                bundle.putInt("id", it)
                bundle.putInt("which", 1)
                findNavController().navigate(R.id.action_homeFragment_to_aboutMovieFragment, bundle)
            }
        }
    }

    private fun rvPopularRefresh(listPopular: java.util.ArrayList<Result>) {
        binding.apply {
            adapterPopular = PopularAdapter(listPopular)
            rvPopular.adapter = adapterPopular
            rvPopular.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterPopular.detail = {
                val bundle = Bundle()
                bundle.putInt("id", it)
                bundle.putInt("which", 1)
                findNavController().navigate(R.id.action_homeFragment_to_aboutMovieFragment, bundle)
            }
        }
    }

    private fun rvNowPlayingRefresh(list: java.util.ArrayList<Result>) {
        binding.apply {
            adapterNowPlaying = NowPlayingAdapter(list)
            rvNowPlaying.adapter = adapterNowPlaying
            rvNowPlaying.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun loadPopular() {
        ApiClient.apiService.getPopular(1)
            .enqueue(object : Callback<Popular> {
                override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                    if (response.isSuccessful) {
                        listPopular = response.body()!!.results
                        adapterPopular.submitList(listPopular)
                    }
                }

                override fun onFailure(call: Call<Popular>, t: Throwable) {
                }
            })
    }

    private fun loadTopRated() {
        ApiClient.apiService.getTopRated(1)
            .enqueue(object : Callback<Popular> {
                override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                    if (response.isSuccessful) {
                        listTopRated = response.body()!!.results
                        adapterTopRated.submitList(listTopRated)
                    }
                }

                override fun onFailure(call: Call<Popular>, t: Throwable) {
                }
            })
    }

    private fun loadUpcoming() {
        ApiClient.apiService.getUpcoming(1)
            .enqueue(object : Callback<Popular> {
                override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                    if (response.isSuccessful) {
                        listUpcoming = response.body()!!.results
                        adapterUpcoming.submitList(listUpcoming)
                    }
                }

                override fun onFailure(call: Call<Popular>, t: Throwable) {
                }
            })
    }

    private fun loadNowPlaying() {
        ApiClient.apiService.getNowPlaying(1).enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if (response.isSuccessful) {
                    listNowPlaying = response.body()!!.results
                    adapterNowPlaying.submitlist(listNowPlaying)
                }
            }

            override fun onFailure(call: Call<Popular>, t: Throwable) {

            }

        })
    }


    private fun isInternetAviable(): Boolean {
        val manager = activity?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }


}