package com.manubla.freemarket.view.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.R
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4::class)
class ViewExtensionTest {
    private lateinit var view: View

    @Before
    fun setUp() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        view = LayoutInflater.from(context).inflate(R.layout.view_unknown_item, null)
    }

    @Test
    @Throws(Exception::class)
    fun `validate view extensions`() {
        view.visible()
        assertThat(view.visibility, equalTo(View.VISIBLE))

        view.gone()
        assertThat(view.visibility, equalTo(View.GONE))

        view.visibleIf(true)
        assertThat(view.visibility, equalTo(View.VISIBLE))

        view.visibleIf(false)
        assertThat(view.visibility, equalTo(View.INVISIBLE))

        view.invisibleIf(true)
        assertThat(view.visibility, equalTo(View.INVISIBLE))

        view.invisibleIf(false)
        assertThat(view.visibility, equalTo(View.VISIBLE))
    }

}