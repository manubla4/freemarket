package com.manubla.freemarket.data.source.storage.datastore

import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabaseImpl
import com.manubla.freemarket.mock.getMockPagingSource
import com.manubla.freemarket.mock.getMockProduct
import com.manubla.freemarket.mock.getMockProductQuery
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class ProductDataStoreDatabaseTest {

    private val dao = mock(ProductDao::class.java)
    private val datastore: ProductDataStoreDatabase = ProductDataStoreDatabaseImpl(dao)

    @Test
    fun `validate fetch product`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenReturn(
                getMockProductQuery()
            )
            val actual = datastore.getProductById(anyString())
            verify(dao).getById(anyString())
            assertEquals(getMockProductQuery(), actual)
        }
    }

    @Test
    fun `validate fetch product with error`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.getProductById(anyString())
            verify(dao).getById(anyString())
            assertEquals(null, actual)
        }
    }

    @Test
    fun `validate fetch all products`() {
        val expected = getMockPagingSource()
        runBlocking {
            `when`(
                dao.getPagingSource()
            ).thenReturn(
                expected
            )
            val actual = datastore.getProducts()
            verify(dao).getPagingSource()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `validate store product`() {
        runBlocking {
            val product = getMockProduct()
            datastore.storeProduct(product)
            verify(dao).insert(product)
        }
    }

    @Test
    fun `validate store products`() {
        runBlocking {
            val products = listOf(getMockProduct())
            datastore.storeProducts(products)
            verify(dao).insertAll(products)
        }
    }


    @Test
    fun `validate clear storage`() {
        runBlocking {
            datastore.clearStorage()
            verify(dao).deleteAll()
        }
    }

}