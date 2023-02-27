package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.AllMovieAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentAllMovieBinding
import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.model.Result
import com.uzb7.moviedb.utils.viewBinding
import com.uzb7.mycats.utils.EndlessRecyclerViewScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllMovieFragment : Fragment(R.layout.fragment_all_movie) {
    private val binding by viewBinding { FragmentAllMovieBinding.bind(it) }
    private val args : AllMovieFragmentArgs by navArgs()
    var listAllType = ArrayList<Result>()
    lateinit var adapterMovieType: AllMovieAdapter
    private var page=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val movieType=args.movieType
        binding.apply {
            val manager=GridLayoutManager(requireContext(),2)
            if(movieType=="popular"){
                loadPopular()
                adapterMovieType = AllMovieAdapter(listAllType)
                rvAllMovieType.adapter = adapterMovieType
                rvAllMovieType.layoutManager =manager
                val scrollListener = object : EndlessRecyclerViewScrollListener(manager){
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        loadPopular()
                    }
                }
                rvAllMovieType.addOnScrollListener(scrollListener)

            }
            else if(movieType=="top_rated"){
                loadTopRated()
                adapterMovieType = AllMovieAdapter(listAllType)
                rvAllMovieType.adapter = adapterMovieType
                rvAllMovieType.layoutManager =manager
                val scrollListener = object : EndlessRecyclerViewScrollListener(manager){
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        loadTopRated()
                    }
                }
                rvAllMovieType.addOnScrollListener(scrollListener)

            }
            else if(movieType=="upcoming"){
                loadUpcoming()
                adapterMovieType = AllMovieAdapter(listAllType)
                rvAllMovieType.adapter = adapterMovieType
                rvAllMovieType.layoutManager =manager
                val scrollListener = object : EndlessRecyclerViewScrollListener(manager){
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        loadUpcoming()
                    }
                }
                rvAllMovieType.addOnScrollListener(scrollListener)
            }
        }

    }

    private fun loadPopular() {
        ApiClient.apiService.getPopular("99b4808386d0dc2136f0e6efe977a911",getPages()).enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if(response.isSuccessful){
                    listAllType=response.body()!!.results
                    adapterMovieType.submitList(listAllType)
                }
            }
            override fun onFailure(call: Call<Popular>, t: Throwable) {
            }
        })
    }
    private fun loadTopRated() {
        ApiClient.apiService.getTopRated("99b4808386d0dc2136f0e6efe977a911",getPages()).enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if(response.isSuccessful){
                    listAllType=response.body()!!.results
                    adapterMovieType.submitList(listAllType)
                }
            }
            override fun onFailure(call: Call<Popular>, t: Throwable) {
            }
        })
    }
    private fun loadUpcoming() {
        ApiClient.apiService.getUpcoming("99b4808386d0dc2136f0e6efe977a911",getPages()).enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if(response.isSuccessful){
                    listAllType=response.body()!!.results
                    adapterMovieType.submitList(listAllType)
                }
            }
            override fun onFailure(call: Call<Popular>, t: Throwable) {
            }
        })
    }
    private fun getPages(): Int {
        return page++
    }
}