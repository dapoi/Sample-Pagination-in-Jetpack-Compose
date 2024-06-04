package com.dapascript.newscompose.ui.screen.news.education

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapascript.newscompose.data.entity.EduNewsEntity
import com.dapascript.newscompose.ui.component.CustomError
import com.dapascript.newscompose.ui.component.CustomLoading
import com.dapascript.newscompose.ui.component.CustomSnackbar
import com.dapascript.newscompose.ui.component.EduNewsComponent
import com.dapascript.newscompose.utils.ListState

@Composable
fun EduNewsScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: EduNewsViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) = with(viewModel) {
    val lazyListState = rememberLazyListState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            isCanPaginate && lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listIDNews.size - 1
        }
    }

    LaunchedEffect(shouldStartPaginate.value) {
        if (shouldStartPaginate.value && listState == ListState.IDLE) getIDNews()
    }

    EduNewsContent(
        snackbarHostState = snackbarHostState,
        listIDNews = listIDNews,
        listState = listState,
        lazyListState = lazyListState,
        navigateToDetail = navigateToDetail,
        retry = ::getIDNews
    )
}

@Composable
private fun EduNewsContent(
    snackbarHostState: SnackbarHostState,
    listIDNews: List<EduNewsEntity>,
    listState: ListState,
    lazyListState: LazyListState,
    navigateToDetail: (String) -> Unit,
    retry: () -> Unit
) {
    LaunchedEffect(listState) {
        if (listState == ListState.PAGINATING) {
            lazyListState.animateScrollToItem(listIDNews.size)
        }
    }

    LazyColumn(
        state = lazyListState
    ) {
        items(listIDNews) { EduNewsComponent(newsEntity = it, navigateToDetail = navigateToDetail) }

        if (listState == ListState.PAGINATING) item { CustomLoading(Modifier.fillMaxWidth()) }
        else if (listState == ListState.PAGINATION_EXHAUST) item {
            CustomSnackbar(
                snackbarHostState = snackbarHostState,
                message = "No more news to show"
            )
        }
    }

    if (listState == ListState.ERROR) CustomError { retry() }
    else if (listState == ListState.LOADING) CustomLoading(Modifier.fillMaxSize())
}