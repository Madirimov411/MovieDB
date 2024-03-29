package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzb7.moviedb.R
import com.uzb7.moviedb.adapter.ReviewAdapter
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentReviewBinding
import com.uzb7.moviedb.model.review.Review
import com.uzb7.moviedb.utils.Extension.hide
import com.uzb7.moviedb.utils.Extension.show
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
        val which=args.which
        binding.apply {
            loadReview(id)
            adapter= ReviewAdapter(list)
            rvReview.adapter=adapter
            rvReview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            ivBack.setOnClickListener {
                //requireActivity().onBackPressed()
                val bundle=Bundle()
                bundle.putInt("id",id)
                bundle.putInt("which",which)
                findNavController().navigate(R.id.action_reviewFragment_to_aboutMovieFragment,bundle)
            }
            requireActivity().onBackPressedDispatcher.addCallback(this@ReviewFragment){
                val bundle=Bundle()
                bundle.putInt("id",id)
                bundle.putInt("which",which)
                findNavController().navigate(R.id.action_reviewFragment_to_aboutMovieFragment,bundle)
            }
        }
    }

    private fun loadReview(id:Int) {
        ApiClient.apiService.getAllReview(id,1).enqueue(object :Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                if(response.isSuccessful){
                    list = response.body()!!.results
                    if(list!=null) {
                        binding.tvNoReview.hide()
                        binding.rvReview.show()
                        adapter.submitList(list)
                    }
                    else{
                        noReview()
                    }
                }
            }
            override fun onFailure(call: Call<Review>, t: Throwable) {
            }
        })
    }

    private fun noReview() {
        binding.apply {
            rvReview.hide()
            tvNoReview.show()
        }
    }

}