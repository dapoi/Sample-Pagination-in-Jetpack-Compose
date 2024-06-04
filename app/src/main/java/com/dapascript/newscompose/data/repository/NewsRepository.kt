package com.dapascript.newscompose.data.repository

import androidx.paging.PagingData
import com.dapascript.newscompose.data.entity.EduNewsPage
import com.dapascript.newscompose.data.entity.TechNewsEntity
import com.dapascript.newscompose.utils.BaseState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTechNews(): Flow<PagingData<TechNewsEntity>>
    fun getEduNews(page: Int): Flow<BaseState<EduNewsPage>>
}