package com.manubla.freemarket.data.source.network.datastore

import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.ProductService
import com.manubla.freemarket.mock.getMockProduct
import com.manubla.freemarket.mock.getMockProductPage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class ProductDataStoreNetworkTest {

    private val service = mock(ProductService::class.java)
    private val datastore: ProductDataStoreNetwork = ProductDataStoreNetworkImpl(service)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch product with network`() {
        runBlocking {
            `when`(
                service.fetchProduct(anyString())
            ).thenReturn(
                getMockProduct()
            )
            val actual = datastore.fetchProductById(anyString())
            verify(service).fetchProduct(anyString())
            assertEquals(getMockProduct(), actual)
        }
    }

    @Test
    fun `validate fetch product without network`() {
        runBlocking {
            `when`(
                service.fetchProduct(anyString())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.fetchProductById(anyString())
            verify(service).fetchProduct(anyString())
            assertEquals(null, actual)
        }
    }


    @Test
    fun `validate fetch products page with network`() {
        val siteId = anyString()
        val query = anyString()
        val offset = anyInt()
        val limit = anyInt()
        runBlocking {
            `when`(
                service.fetchProductsPage(siteId, query, offset, limit)
            ).thenReturn(
                getMockProductPage()
            )
            val actual = datastore.fetchProductsPage(query, offset, limit)
            verify(service).fetchProductsPage(BuildConfig.SITE_ID, query, offset, limit)
            assertEquals(getMockProductPage(), actual)
        }
    }

    @Test
    fun `validate fetch products page without network`() {
        val siteId = anyString()
        val query = anyString()
        val offset = anyInt()
        val limit = anyInt()
        runBlocking {
            `when`(
                service.fetchProductsPage(siteId, query, offset, limit)
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.fetchProductsPage(query, offset, limit)
            verify(service).fetchProductsPage(BuildConfig.SITE_ID, query, offset, limit)
            assertEquals(null, actual)
        }
    }
}