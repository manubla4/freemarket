package com.manubla.freemarket.view.fragment

import android.os.Bundle
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentSplashBinding
import com.manubla.freemarket.view.event.SplashState
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : ViewDataBindingFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(viewDataBinding: FragmentSplashBinding, savedInstanceState: Bundle?) {
        super.onViewCreated(viewDataBinding, savedInstanceState)
        setObservers()
        viewModel.fetchInitialData()
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, {
            if (it == SplashState.Done) {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        })
    }

}