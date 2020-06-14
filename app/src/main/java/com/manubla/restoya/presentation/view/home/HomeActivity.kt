package com.manubla.restoya.presentation.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.manubla.restoya.R
import com.manubla.restoya.presentation.view.map.MapFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {

            val homeFragment = HomeFragment()
            val favoritesFragment = MapFragment()

            showFragment(HomeFragment(), HomeFragmentTag)
            bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
                removeActiveFragment()
                when (menuItem.itemId) {
                    R.id.home -> showFragment(homeFragment, HomeFragmentTag)
                    R.id.map -> showFragment(favoritesFragment, MapFragmentTag)
                }
                true
            }
        }
    }

    private fun removeActiveFragment() {
        listOf(HomeFragmentTag, MapFragmentTag).forEach { tag ->
            val fragment = supportFragmentManager.findFragmentByTag(tag)
            fragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .remove(it)
                    .commit()
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }

    companion object {
        private const val HomeFragmentTag       = "HomeFragmentTag"
        private const val MapFragmentTag        = "MapFragmentTag"
    }
}
