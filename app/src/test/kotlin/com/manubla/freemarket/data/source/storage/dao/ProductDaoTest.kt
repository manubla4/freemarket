package com.manubla.freemarket.data.source.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.data.model.business.Picture
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.mock.getMockCurrencies
import com.manubla.freemarket.mock.getMockProduct
import com.manubla.freemarket.mock.getMockProducts
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var productDao: ProductDao
    private lateinit var db: AppDatabase
    private var mockCurrencies = getMockCurrencies()

    @Before
    fun createDb() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDao = db.productDao()
        currencyDao = db.currencyDao()
        runBlocking {
            currencyDao.insertAll(mockCurrencies)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `validate insert update and fetch product`() {
        runBlocking {
            val product = getMockProduct()
            val currency = mockCurrencies.firstOrNull { it.id == product.currencyId }
            productDao.insert(product)

            val actual = productDao.getById(product.id)
            val expected = ProductCurrencyQuery(product.id, product.title,
                product.price, product.condition, product.thumbnail,
                product.warranty, currency?.symbol, product.pictures,
                product.freeShipping, product.sellerId)
            assertThat(actual, equalTo(expected))

            val mockPictures = listOf(Picture(null), Picture(null))
            val mockWarranty = "warranty"
            val mockSellerId = 1234L
            productDao.update(
                id = product.id,
                pictures = mockPictures,
                warranty = mockWarranty,
                sellerId = mockSellerId
            )
            val actualUpdated = productDao.getById(product.id)
            assertThat(actualUpdated?.pictures, equalTo(mockPictures))
            assertThat(actualUpdated?.warranty, equalTo(mockWarranty))
            assertThat(actualUpdated?.sellerId, equalTo(mockSellerId))

            val actualTotal = productDao.getAll()
            assertThat(actualTotal.size, equalTo(1))
        }
    }

    @Test
    @Throws(Exception::class)
    fun `validate insert and fetch products`() {
        runBlocking {
            val products = getMockProducts()
            productDao.insertAll(products)

            for (product in products) {
                val currency = mockCurrencies.firstOrNull { it.id == product.currencyId }
                val actual = productDao.getById(product.id)
                val expected = ProductCurrencyQuery(product.id, product.title,
                    product.price, product.condition, product.thumbnail,
                    product.warranty, currency?.symbol, product.pictures,
                    product.freeShipping, product.sellerId)
                assertThat(actual, equalTo(expected))
            }

            val actual = productDao.getAll()
            assertThat(actual.size, equalTo(products.size))
        }
    }

    @Test
    @Throws(Exception::class)
    fun `validate delete products`() {
        runBlocking {
            val products = getMockProducts()

            productDao.insertAll(products)
            val actualInserted = productDao.getAll()
            assertThat(actualInserted.size, equalTo(products.size))

            productDao.deleteAll()
            val actualDeleted = productDao.getAll()
            assertThat(actualDeleted.size, equalTo(0))
        }
    }
}