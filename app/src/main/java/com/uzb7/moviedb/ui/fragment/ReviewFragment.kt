package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uzb7.moviedb.R
import com.uzb7.moviedb.data.remote.ApiClient
import com.uzb7.moviedb.databinding.FragmentReviewBinding
import com.uzb7.moviedb.utils.viewBinding

class ReviewFragment : Fragment(R.layout.fragment_review) {
    private val list=ArrayList<com.uzb7.moviedb.model.review.Result>()
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
            ivBack.setOnClickListener {
                val bundle=Bundle()
                bundle.putInt("id",id)
                findNavController().navigate(R.id.action_reviewFragment_to_aboutMovieFragment)
            }
        }
    }

    private fun loadReview(id:Int) {
        //ApiClient.apiService.getAllReview(id,1)
    }

}