package com.manubla.freemarket.data.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.result.SearchResult
import com.manubla.freemarket.data.repository.base.SourceRepository
import com.manubla.freemarket.extension.isZero
import com.manubla.freemarket.extension.zero

internal class ModelPagingSource(
    private val dataSource: SourceRepository,
    private val pagerRequest: PagerRequest
) : PagingSource<Int, Model>() {

    var currentPage = Int.zero()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Model> {
        return try {
            currentPage = params.key ?: Int.zero()
            val result = dataSource.fetchData(
                query = pagerRequest.query,
                page = currentPage
            )
            result?.let { safeResult ->
                if (params.isFirstPage() && safeResult.isEmpty()) {
                    emptyItem()
                } else {
                    pageResult(safeResult)
                }
            } ?: emptyItem()
        } catch (exception: Exception) {
            Log.e(TAG_LOAD_PRODUCT_PAGE, Log.getStackTraceString(exception))
            if (params.isFirstPage()) {
                errorItem()
            } else {
                LoadResult.Error(exception)
            }
        }
    }

    private fun LoadParams<Int>.isFirstPage() = key == null

    override fun getRefreshKey(state: PagingState<Int, Model>): Int? = null

    private fun emptyItem(): LoadResult.Page<Int, Model> {
        val empty = SearchResult(SearchResult.STATE_EMPTY)
        return pageResult(listOf(empty))
    }

    private fun errorItem(): LoadResult.Page<Int, Model> {
        val error = SearchResult(SearchResult.STATE_ERROR)
        return pageResult(listOf(error))
    }

    private fun pageResult(
        result: List<Model>
    ): LoadResult.Page<Int, Model> {
        return LoadResult.Page(
            data = result,
            prevKey = getPrevKey(),
            nextKey = getNextKey()
        )
    }

    private fun getPrevKey(): Int? {
        return if (currentPage.isZero()) {
            null
        } else currentPage - 1
    }

    private fun getNextKey(): Int = currentPage + 1


    companion object {
        private const val TAG_LOAD_PRODUCT_PAGE = "LOAD_PRODUCT_PAGE"
    }
}