package com.manubla.freemarket.data.repository

import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.repository.product.ProductSourceRepositoryImpl
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.mock.getMockProduct
import com.manubla.freemarket.mock.getMockProductQuery
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class ProductSourceRepositoryTest {

    private val database = mock(ProductDataStoreDatabase::class.java)
    private val network = mock(ProductDataStoreNetwork::class.java)
    private val repository: ProductSourceRepository = ProductSourceRepositoryImpl(database, network)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch product with network and database`() {
        runBlocking {
            `when`(
                network.fetchProductById(anyString())
            ).thenReturn(
                getMockProduct()
            )
            `when`(
                database.getProductById(anyString())
            ).thenReturn(
                getMockProductQuery()
            )
            val actual = repository.fetchProduct(anyString())
            verify(network).fetchProductById(anyString())
            verify(database).storeProduct(getMockProduct())
            verify(database).getProductById(anyString())
            assertEquals(getMockProductQuery(), actual)
        }
    }

    @Test
    fun `validate fetch product without network but with database`() {
        runBlocking {
            `when`(
                network.fetchProductById(anyString())
            ).thenReturn(
               null
            )
            `when`(
                database.getProductById(anyString())
            ).thenReturn(
                getMockProductQuery()
            )
            val actual = repository.fetchProduct(anyString())
            verify(network).fetchProductById(anyString())
            verify(database, times(0)).storeProduct(getMockProduct())
            verify(database).getProductById(anyString())
            assertEquals(getMockProductQuery(), actual)
        }
    }


    @Test
    fun `validate fetch product without network or database`() {
        runBlocking {
            `when`(
                network.fetchProductById(anyString())
            ).thenReturn(
                null
            )
            `when`(
                database.getProductById(anyString())
            ).thenReturn(
                null
            )
            val actual = repository.fetchProduct(anyString())
            verify(network).fetchProductById(anyString())
            verify(database, times(0)).storeProduct(getMockProduct())
            verify(database).getProductById(anyString())
            assertEquals(null, actual)
        }
    }

}