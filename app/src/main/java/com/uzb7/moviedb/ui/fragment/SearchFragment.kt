package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.SearchAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentSearchBinding
import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.utils.Extension.hide
import com.uzb7.moviedb.utils.Extension.show
import com.uzb7.moviedb.utils.viewBinding
import com.uzb7.mycats.utils.EndlessRecyclerViewScrollListener
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(R.layout.fragment_search) {
    private var pages = 0
    var list = ArrayList<com.uzb7.moviedb.model.Result>()
    lateinit var adapter: SearchAdapter
    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val manager = GridLayoutManager(requireContext(), 2)
            etSearch.addTextChangedListener {
                try {
                    loadSearch(etSearch.text.toString())
                    adapter = SearchAdapter(list)
                    rvSearch.adapter = adapter
                    rvSearch.layoutManager = manager
                    adapter.detail = {
                        val bundle = Bundle()
                        bundle.putInt("id", it)
                        bundle.putInt("which", 3)
                        findNavController().navigate(
                            R.id.action_searchFragment_to_aboutMovieFragment,
                            bundle
                        )
                    }
                    val scrollListener = object : EndlessRecyclerViewScrollListener(manager) {
                        override fun onLoadMore(
                            page: Int,
                            totalItemsCount: Int,
                            view: RecyclerView?
                        ) {
                            loadSearch(etSearch.text.toString())
                        }
                    }
                    rvSearch.addOnScrollListener(scrollListener)
                } finally {

                }

            }
            ivBack.setOnClickListener {

            }

        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadSearch(s: String) {
        binding.animationView.show()
        ApiClient.apiService.getSearch(s, getPage(pages)).enqueue(object : Callback<Popular> {
            override fun onResponse(call: Call<Popular>, response: Response<Popular>) {
                if (response.isSuccessful) {
                    list = response.body()!!.results
                    adapter.submitList(list)
                    binding.animationView.hide()
                }
            }

            override fun onFailure(call: Call<Popular>, t: Throwable) {

            }


        })
    }

    private fun getPage(page: Int): Int {
        pages = page + 1
        return pages
    }


}