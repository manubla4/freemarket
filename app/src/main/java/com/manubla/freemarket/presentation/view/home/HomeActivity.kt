package com.manubla.freemarket.presentation.view.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.manubla.freemarket.R
import com.manubla.freemarket.presentation.view.map.MapFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener {

    private lateinit var prevMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {

            setSupportActionBar(toolbar as Toolbar?)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            val homeFragment = HomeFragment()
            val mapFragment = MapFragment()

            viewpager.apply {
                offscreenPageLimit = POSITION_MAP
                adapter = HomeViewPagerAdapter(supportFragmentManager).apply {
                    addFragment(homeFragment)
                    addFragment(mapFragment)
                }
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                    override fun onPageScrollStateChanged (state: Int) { }

                    override fun onPageScrolled (position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int) { }

                    override fun onPageSelected (position: Int) {
                        if (::prevMenuItem.isInitialized)
                            prevMenuItem.isChecked = false
                        else
                            bottomNavigation.menu.getItem(0).isChecked = false

                        bottomNavigation.menu.getItem(position).isChecked = true
                        prevMenuItem = bottomNavigation.menu.getItem(position)
                    }
                })
            }

            bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home -> viewpager.currentItem = POSITION_HOME
                    R.id.map -> viewpager.currentItem = POSITION_MAP
                }
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_refresh) onRefresh()
        return super.onOptionsItemSelected(item)
    }

    private fun onRefresh() {
        val fragment = (viewpager.adapter as HomeViewPagerAdapter)
                                    .getItem(viewpager.currentItem)
        (fragment as ActivityCallback).onRefresh()
    }

    override fun onFragmentInteraction(lat: Double?, long: Double?) {
        val fragment = (viewpager.adapter as HomeViewPagerAdapter).getItem(0)
        (fragment as ActivityCallback).onChangeLocation(lat, long)
    }

    companion object {
        private const val POSITION_HOME     = 0
        private const val POSITION_MAP      = 1
    }

}

interface ActivityCallback {
    fun onRefresh()
    fun onChangeLocation(lat: Double?, long: Double?)
}
