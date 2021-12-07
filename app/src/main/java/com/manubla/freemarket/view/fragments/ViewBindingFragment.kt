package com.manubla.freemarket.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class ViewBindingFragment<V : ViewBinding>(
    @LayoutRes private val layout: Int
) : Fragment() {

    protected lateinit var viewBinding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = viewBinding(layout, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(viewBinding, savedInstanceState)
    }

    abstract fun onViewCreated(viewDataBinding: V, savedInstanceState: Bundle?)

    private fun <V : ViewDataBinding> Fragment.viewBinding(
        @LayoutRes layout: Int,
        container: ViewGroup?
    ): V {
        val viewBinding: V = DataBindingUtil.inflate(layoutInflater, layout, container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding
    }

}
