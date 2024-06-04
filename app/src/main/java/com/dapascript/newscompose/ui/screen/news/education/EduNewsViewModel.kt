package com.dapascript.newscompose.ui.screen.news.education

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dapascript.newscompose.data.entity.EduNewsEntity
import com.dapascript.newscompose.data.repository.NewsRepository
import com.dapascript.newscompose.utils.BaseState
import com.dapascript.newscompose.utils.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EduNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var page by mutableIntStateOf(1)
     var isCanPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)
    val listIDNews = mutableStateListOf<EduNewsEntity>()

    init {
        getIDNews()
    }

    fun getIDNews() = viewModelScope.launch {
        if (page == 1 || (page != 1 && isCanPaginate) && listState == ListState.IDLE) {
            newsRepository.getEduNews(page).collect { state ->
                when (state) {
                    is BaseState.Loading -> {
                        listState = if (page == 1) ListState.LOADING else ListState.PAGINATING
                    }

                    is BaseState.Success -> {
                        isCanPaginate = state.data.page < state.data.totalPages

                        if (page == 1) {
                            listIDNews.clear()
                            listIDNews.addAll(state.data.results)
                        } else {
                            listIDNews.addAll(state.data.results)
                        }

                        listState = ListState.IDLE

                        if (isCanPaginate) page++
                    }

                    is BaseState.Error -> {
                        listState = if (page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST
                    }
                }
            }
        }
    }

    override fun onCleared() {
        page = 1
        listState = ListState.IDLE
        isCanPaginate = false
        super.onCleared()
    }
}