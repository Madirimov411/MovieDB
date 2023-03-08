package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.ReviewAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentReviewBinding
import com.uzb7.moviedb.model.review.Review
import com.uzb7.moviedb.utils.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : Fragment(R.layout.fragment_review) {
    private var list=ArrayList<com.uzb7.moviedb.model.review.Result>()
    lateinit var  adapter:ReviewAdapter
    private val binding by viewBinding { FragmentReviewBinding.bind(it) }
    private val args:ReviewFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val id=args.id

        binding.apply {
            loadReview(id)
            adapter= ReviewAdapter(list)
            rvReview.adapter=adapter
            rvReview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            ivBack.setOnClickListener {
                //requireActivity().onBackPressed()
                val bundle=Bundle()
                bundle.putInt("id",id)
                findNavController().navigate(R.id.action_reviewFragment_to_aboutMovieFragment,bundle)
            }
        }
    }

    private fun loadReview(id:Int) {
        ApiClient.apiService.getAllReview(id,1).enqueue(object :Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                list=response.body()!!.results
                adapter.submitList(list)
            }

            override fun onFailure(call: Call<Review>, t: Throwable) {

            }

        })
    }

}