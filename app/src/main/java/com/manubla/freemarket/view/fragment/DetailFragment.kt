package com.manubla.freemarket.view.fragment

import android.os.Bundle
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentHomeBinding
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : ViewDataBindingFragment<FragmentHomeBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}