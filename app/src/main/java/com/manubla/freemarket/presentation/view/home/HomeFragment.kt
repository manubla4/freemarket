package com.manubla.freemarket.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.R
import com.manubla.freemarket.data.model.Restaurant
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse
import com.manubla.freemarket.presentation.util.showLongErrorMessage
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(), HomeAdapter.OnAdapterInteraction, ActivityCallback {

    private lateinit var mAdapter: HomeAdapter
    private val mViewModel: HomeViewModel by viewModel()
    private var mCurrentOffset = 0
    private var mCurrentTotal = 0
    private var mLoading = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainLayout.requestFocus()
        mAdapter = HomeAdapter(this)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = mAdapter
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                        if (!mLoading) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                mLoading = true
                                mAdapter.addItem(Any())  //Progress item
                                mViewModel.loadData(mCurrentOffset)
                            }
                        }
                    }
                }
            })
        }
        swipeLayout.isRefreshing = true

        mViewModel.data.observe(viewLifecycleOwner, Observer(this::dataChanged))
        mViewModel.networkOnline.observe(viewLifecycleOwner, Observer(this::isNetworkOnline))
        mViewModel.loadData(mCurrentOffset)

        swipeLayout.setOnRefreshListener {
            doRefresh(null, null)
        }
    }

    override fun onChangeLocation(lat: Double?, long: Double?) {
        doRefresh(lat, long)
    }

    override fun onRefresh() {
        doRefresh(null, null)
    }


    private fun doRefresh(lat: Double?, long: Double?) {
        swipeLayout.isRefreshing = true
        mLoading = true
        mAdapter.restaurants = arrayListOf()
        mCurrentOffset = 0
        mViewModel.loadData(mCurrentOffset, lat, long)
    }


    private fun isNetworkOnline(network: Boolean) {
        if (!network)
            showLongErrorMessage(getString(R.string.txt_network_connection), view, context)
    }


    private fun dataChanged(page: RestaurantsPageResponse) {

        var changedNetworkStatus = false
        mAdapter.removeProgressItem()
        page.total?.let {
            if (mCurrentTotal > 0 && it != mCurrentTotal) {
                //User activated or deactivated network while scrolling
                mCurrentOffset = 0
                mAdapter.restaurants = arrayListOf()
                changedNetworkStatus = true
            }
            mCurrentTotal = it
        }

        swipeLayout.isRefreshing = false
        if (mCurrentOffset == 0) {
            recyclerView.scheduleLayoutAnimation()
            mViewModel.isNetworkOnline()
        }

        if (changedNetworkStatus)
            mViewModel.loadData(mCurrentOffset)
        else {
            mCurrentOffset += page.count
            mAdapter.addRestaurantItems(page.data)
        }
        mLoading = false
    }

    override fun onSelectRestaurant(restaurant: Restaurant?) {
        //TODO show in map
    }

}
