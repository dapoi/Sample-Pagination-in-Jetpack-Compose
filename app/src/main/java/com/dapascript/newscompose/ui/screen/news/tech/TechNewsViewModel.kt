package com.dapascript.newscompose.ui.screen.news.tech

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dapascript.newscompose.data.entity.TechNewsEntity
import com.dapascript.newscompose.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _listUSNewsState = MutableStateFlow<PagingData<TechNewsEntity>>(PagingData.empty())
    val listUSNewsState = _listUSNewsState.asStateFlow()

    fun getUSNews() = viewModelScope.launch {
        newsRepository.getTechNews()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { _listUSNewsState.value = it }
    }
}