package com.manubla.freemarket.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.manubla.freemarket.R
import com.manubla.freemarket.view.adapter.paging.NavigateCallback


open class ViewDataBindingFragment<V : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : Fragment(), NavigateCallback {

    protected lateinit var viewDataBinding: V
    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = viewDataBinding(layout, container)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(viewDataBinding, savedInstanceState)
    }

    open fun onViewCreated(viewDataBinding: V, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(viewDataBinding.root)
    }

    private fun <V : ViewDataBinding> Fragment.viewDataBinding(
        @LayoutRes layout: Int,
        container: ViewGroup?
    ): V {
        val viewDataBinding: V = DataBindingUtil.inflate(layoutInflater, layout, container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding.unbind()
    }

    override fun onNavigate(destination: String, data: String) {
        val bundle = bundleOf(ARG_DATA to data)
        when (destination) {
            NavigateCallback.DESTINATION_DETAIL -> {
                navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        }
    }

    companion object {
        const val ARG_DATA = "data"
    }

}
