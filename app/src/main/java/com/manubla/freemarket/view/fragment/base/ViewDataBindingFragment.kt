package com.manubla.freemarket.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


open class ViewDataBindingFragment<V : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : Fragment() {

    protected lateinit var viewDataBinding: V

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

    open fun onViewCreated(viewDataBinding: V, savedInstanceState: Bundle?) {}

    private fun <V : ViewDataBinding> Fragment.viewDataBinding(
        @LayoutRes layout: Int,
        container: ViewGroup?
    ): V {
        val viewDataBinding: V = DataBindingUtil.inflate(layoutInflater, layout, container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding
    }

}
