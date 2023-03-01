package com.uzb7.moviedb.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.uzb7.moviedb.R
import com.uzb7.moviedb.databinding.FragmentAboutMovieBinding
import com.uzb7.moviedb.utils.viewBinding


class AboutMovieFragment : Fragment(R.layout.fragment_about_movie) {
    private val binding by viewBinding { FragmentAboutMovieBinding.bind(it) }
    private val args:AboutMovieFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        val id=args.id
        binding.apply {

        }
    }
}