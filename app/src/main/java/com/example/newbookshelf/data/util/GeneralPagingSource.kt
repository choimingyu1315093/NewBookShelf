package com.example.newbookshelf.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.model.post.general.PostModelData

class GeneralPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PostModelData>() {
    override fun getRefreshKey(state: PagingState<Int, PostModelData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModelData> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.postList(BookShelfApp.prefs.getUserIdx("userIdx", 0), 10, currentPage)
            LoadResult.Page(
                data = response.body()!!.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.body()!!.data.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}