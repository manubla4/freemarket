package com.manubla.restoya.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manubla.restoya.R
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(), HomeAdapter.OnAdapterInteraction {

    private lateinit var mAdapter: HomeAdapter
    private val mViewModel: HomeViewModel by viewModel()
    private var mCurrentOffset = 0
    private var mCurrentTotal = 0
    private var mLoading = true
    private var mLatitude: Double? = null
    private var mLongitude: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mLatitude = it.getDouble(KEY_LATITUDE)
            mLongitude = it.getDouble(KEY_LONGITUDE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainLayout.requestFocus()
        mAdapter = HomeAdapter(this)
        mViewModel.data.observe(viewLifecycleOwner, Observer(this::dataChanged))
        val layoutManager = GridLayoutManager(activity, 2)
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
                                mViewModel.loadData(mLatitude, mLongitude, mCurrentOffset)
                            }
                        }
                    }
                }
            })
        }

        swipeLayout.isRefreshing = true
        mViewModel.loadData(mLatitude, mLongitude, mCurrentOffset)
        swipeLayout.setOnRefreshListener {
            doRefresh()
        }
    }

    private fun doRefresh() {
        swipeLayout.isRefreshing = true
        mAdapter.restaurants = arrayListOf()
        mCurrentOffset = 0
        mViewModel.loadData(mLatitude, mLongitude, mCurrentOffset)
    }


    private fun dataChanged(page: RestaurantsPageResponse) {
        mAdapter.removeProgressItem()
        page.total?.let {
            if (it != mCurrentTotal) {
                mCurrentTotal = it
                mAdapter.restaurants = arrayListOf()
            }
        }

        swipeLayout.isRefreshing = false
        mAdapter.addRestaurantItems(page.data)
        if (mCurrentOffset == 0)
            recyclerView.scheduleLayoutAnimation()

        mCurrentOffset += page.count
        mLoading = false
    }

    override fun onSelectRestaurant(restaurant: Restaurant?) {
        //TODO show in map
    }



    companion object {
        private const val KEY_LATITUDE   = "KEY_LATITUDE"
        private const val KEY_LONGITUDE  = "KEY_LONGITUDE"

        @JvmStatic
        fun newInstance(latitude: Double?, longitude: Double?) =
            HomeFragment().apply {
                if (latitude != null && longitude != null) {
                    arguments = Bundle().apply {
                        putDouble(KEY_LATITUDE, latitude)
                        putDouble(KEY_LONGITUDE, longitude)
                    }
                }
            }
    }
}
