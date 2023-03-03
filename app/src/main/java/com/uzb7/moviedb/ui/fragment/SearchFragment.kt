package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.SearchAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentSearchBinding
import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(R.layout.fragment_search) {
    private var pages = 0
    var list=ArrayList<com.uzb7.moviedb.model.Result>()
    lateinit var adapter: SearchAdapter
    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            try{
                etSearch.addTextChangedListener {

                    loadSearch(etSearch.text.toString())
                    adapter = SearchAdapter(list)
                    rvSearch.adapter = adapter
                    rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)

                }
            }finally{

            }

        }
    }

    private fun loadSearch(s: String) {
        ApiClient.apiService.getSearch(s,getPage(pages)).enqueue(object :Callback<Popular>{
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

    private fun getPage(page:Int): Int {
        pages =page+1
        return pages
    }


}