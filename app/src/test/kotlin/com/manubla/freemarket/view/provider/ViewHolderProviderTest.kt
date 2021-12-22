package com.manubla.freemarket.view.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.R
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.mock.getMockProductQuery
import com.manubla.freemarket.mock.getMockSearchResult
import com.manubla.freemarket.mock.getMockState
import com.manubla.freemarket.mock.getMockUser
import com.manubla.freemarket.view.activity.MainActivity
import com.manubla.freemarket.view.viewholder.ErrorViewHolder
import com.manubla.freemarket.view.viewholder.PagingProductViewHolder
import com.manubla.freemarket.view.viewholder.UnknownViewHolder
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4::class)
class ViewHolderProviderTest {

    private lateinit var viewGroup: ViewGroup
    private lateinit var viewHolderModelProvider: ViewHolderModelProvider<Model>

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>().apply { setTheme(R.style.AppTheme) }
        viewGroup = LayoutInflater.from(context).inflate(R.layout.view_unknown_item, null) as ViewGroup
        viewHolderModelProvider = ViewHolderModelProvider()
    }

    @Test
    fun `validate get view types`() {
        val stateType = viewHolderModelProvider.getViewType(getMockState())
        assertEquals(stateType, VIEW_TYPE_STATE)

        val productType = viewHolderModelProvider.getViewType(getMockProductQuery())
        assertEquals(productType, VIEW_TYPE_PRODUCT_QUERY)

        val userType = viewHolderModelProvider.getViewType(getMockUser())
        assertEquals(userType, VIEW_TYPE_USER)

        val resultType = viewHolderModelProvider.getViewType(getMockSearchResult())
        assertEquals(resultType, VIEW_TYPE_SEARCH_RESULT)
    }

    @Test
    @Throws(Exception::class)
    fun `validate create view holder`() {
        activityRule.scenario.onActivity {
            val actualProductVH = viewHolderModelProvider.create(viewGroup, VIEW_TYPE_PRODUCT_QUERY)
            val expectedProductVH = PagingProductViewHolder::class.java
            assertThat(actualProductVH.javaClass, equalTo(expectedProductVH))

            val actualResultVH = viewHolderModelProvider.create(viewGroup, VIEW_TYPE_SEARCH_RESULT)
            val expectedResultVH = ErrorViewHolder::class.java
            assertThat(actualResultVH.javaClass, equalTo(expectedResultVH))

            val actualDefaultVH = viewHolderModelProvider.create(viewGroup, VIEW_TYPE_DEFAULT)
            val expectedDefaultVH = UnknownViewHolder::class.java
            assertThat(actualDefaultVH.javaClass, equalTo(expectedDefaultVH))
        }
    }

}