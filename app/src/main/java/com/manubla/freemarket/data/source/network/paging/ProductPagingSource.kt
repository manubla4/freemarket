package com.manubla.freemarket.data.source.network.paging

import android.util.Log
import android.util.SparseArray
import androidx.core.util.getOrDefault
import androidx.core.util.size
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetworkImpl

internal class ProductPagingSource(
    private val dataSource: ProductSourceRepository
) : PagingSource<Int, Product>() {

    private val pages: SparseArray<String> = SparseArray()

    init {
        pages.clear()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            when (val result = fetchService(params)) {
                is Result.Success -> {
                    val components = result.data
                    if (params.isFirstPage() && components.isEmpty()) {
                        page(generateEmptyComponent())
                    } else {
                        val (nextPageIndex, data) = getKeyAndComponents(components)
                        page(data, nextPageIndex)
                    }
                }
                is Result.Failed -> {
                    val exception = Exception(result.error.message)
                    sendException(exception)
                    generateError(params.isFirstPage(), Throwable(result.error.message))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG_LOAD_PRODUCT_PAGE, Log.getStackTraceString(exception))
            generateError(params.isFirstPage(), exception)
        }
    }

    private fun generateError(
        isFirstPage: Boolean,
        ex: Throwable
    ): LoadResult<Int, Component> {
        return if (isFirstPage) {
            page(generateErrorComponent())
        } else {
            LoadResult.Error(ex)
        }
    }

    private fun getKeyAndComponents(result: List<Component>): Pair<Int?, List<Component>> {
        val nextPageIndex = getNextPageIndex(result)
        val components = result.filterNot { excludeComponentType.contains(it.componentType) }
        return nextPageIndex to components
    }

    private fun page(product: Product) = page(listOf(component))

    private fun page(
        components: List<Component>,
        nextIndex: Int? = null
    ) = LoadResult.Page(
        data = components,
        prevKey = null,
        nextKey = nextIndex
    )

    private suspend fun fetchService(params: LoadParams<Int>): Result<List<Component>> {
        val pageKey = params.key
        return if (pageKey == null) {
            val componentsInCache = localDataSource.getBodyComponents()
            if (componentsInCache.isNullOrEmpty()) {
                localDataSource.getMainRootResult()?.
                    let { processResult(it) } ?: fetchFirstPage()
            } else {
                Result.Success(componentsInCache)
            }
        } else {
            val nextPage = pages.getOrDefault(pageKey, String.empty())
            fetchNextPage(nextPage)
        }
    }

    private suspend fun fetchFirstPage(): Result<List<Component>> {
        val result =
            remoteDataSource.fetchRootComponent(
                businessType = request.businessType,
                countryId = request.countryId,
                point = request.point,
                commandId = request.commandId,
                searchQuery = request.searchQuery,
                selectedFilters = request.selectedFilters,
                hash = request.hash
            )
        return processResult(result)
    }

    private suspend fun fetchNextPage(nextPage: String): Result<List<Component>> {
        if (nextPage.isBlank()) return Result.Success(emptyList())

        val result = remoteDataSource.fetchRootComponent(
            businessType = request.businessType,
            countryId = request.countryId,
            point = request.point,
            commandId = request.commandId,
            searchQuery = request.searchQuery,
            selectedFilters = request.selectedFilters,
            hash = nextPage
        )
        return processResult(result)
    }

    private suspend fun processResult(
        result: Result<Component>
    ): Result<List<Component>> {
        return when (result) {
            is Result.Success -> {
                val bodyComponents = result.data.extractBodyComponents()
                storeBodyComponents(bodyComponents, true)
                Result.Success(bodyComponents)
            }
            is Result.Failed -> result
        }
    }

    private fun getNextPageIndex(components: List<Product>): Int? {
        val nextUrl = components.firstOrNull { it.componentType == ShopListTypes.PAGING }
            ?.findAction(TypeOfAction.PAGE)?.targetUrl
        return insertPage(nextUrl)
    }

    private fun insertPage(url: String?): Int? {
        if (url == null) return null
        val elementNotExists = pages.indexOfValue(url) == INDEX_NOT_FOUND
        return if (elementNotExists) {
            val index = pages.size + 1
            pages.append(index, url)
            index
        } else {
            null
        }
    }

    private fun LoadParams<Int>.isFirstPage() = key == null

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }

    companion object {
        private const val TAG_LOAD_PRODUCT_PAGE = "LOAD_PRODUCT_PAGE"
    }
}