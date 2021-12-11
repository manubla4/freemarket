package com.manubla.freemarket.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentHomeBinding
import com.manubla.freemarket.view.event.SplashState
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : ViewDataBindingFragment<FragmentHomeBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
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