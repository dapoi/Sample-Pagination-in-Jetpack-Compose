package com.dapascript.newscompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dapascript.newscompose.data.entity.EduNewsEntity
import com.dapascript.newscompose.data.entity.EduNewsPage
import com.dapascript.newscompose.data.entity.TechNewsEntity
import com.dapascript.newscompose.data.source.model.ArticlesItem
import com.dapascript.newscompose.data.source.paging.TechNewsPagingSource
import com.dapascript.newscompose.data.source.service.ApiService
import com.dapascript.newscompose.utils.BaseState
import com.dapascript.newscompose.utils.BaseState.Error
import com.dapascript.newscompose.utils.BaseState.Loading
import com.dapascript.newscompose.utils.BaseState.Success
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NewsRepository {

    override fun getTechNews(): Flow<PagingData<TechNewsEntity>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { TechNewsPagingSource(apiService) }
    ).flow

    override fun getEduNews(page: Int): Flow<BaseState<EduNewsPage>> = flow {
        emit(Loading)
        try {
            val response = apiService.getNews("education", page, 10)
            val articles = response.articles?.map { mapToEntity(it) }
            delay(1000)
            emit(
                Success(
                    EduNewsPage(
                        page = page,
                        results = articles.orEmpty(),
                        totalPages = response.totalResults
                    )
                )
            )
        } catch (e: Throwable) {
            emit(Error(e))
        }
    }.flowOn(IO)
}

private fun mapToEntity(data: ArticlesItem?) = EduNewsEntity(
    title = data?.title.orEmpty(),
    author = data?.author.orEmpty(),
    description = data?.description.orEmpty(),
    url = data?.url.orEmpty(),
    urlToImage = data?.urlToImage.orEmpty()
)