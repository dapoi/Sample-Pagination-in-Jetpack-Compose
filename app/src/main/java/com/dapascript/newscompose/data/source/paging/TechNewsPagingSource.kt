package com.dapascript.newscompose.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dapascript.newscompose.data.entity.TechNewsEntity
import com.dapascript.newscompose.data.source.service.ApiService

class TechNewsPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, TechNewsEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TechNewsEntity> = try {
        val page = params.key ?: 1
        val responseData = apiService.getNews("technology", page, params.loadSize)
        val techNewsEntity = responseData.articles?.map {
            TechNewsEntity(
                title = it?.title.orEmpty(),
                author = it?.author.orEmpty(),
                description = it?.description.orEmpty(),
                url = it?.url.orEmpty(),
                urlToImage = it?.urlToImage.orEmpty()
            )
        }
        LoadResult.Page(
            data = techNewsEntity.orEmpty(),
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page == responseData.totalResults) null else page + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, TechNewsEntity>) = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
}