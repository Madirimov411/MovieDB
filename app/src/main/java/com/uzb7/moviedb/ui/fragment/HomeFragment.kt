package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.PopularAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentHomeBinding
import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    var list = ArrayList<com.uzb7.moviedb.model.Result>()
    lateinit var adapter: PopularAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            loadPopular()
            adapter = PopularAdapter(list)
            rvPopular.adapter = adapter
            rvPopular.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun loadPopular() {
        ApiClient.apiService.getPopular().enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if(response.isSuccessful){
                    list=response.body()!!.results
                    adapter.submitList(list)
                }
            }

            override fun onFailure(call: Call<Popular>, t: Throwable) {

            }


        })
    }

}