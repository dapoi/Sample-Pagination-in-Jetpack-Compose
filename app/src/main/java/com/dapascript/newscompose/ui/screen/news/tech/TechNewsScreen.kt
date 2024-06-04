package com.dapascript.newscompose.ui.screen.news.tech

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dapascript.newscompose.data.entity.TechNewsEntity
import com.dapascript.newscompose.ui.component.CustomError
import com.dapascript.newscompose.ui.component.CustomLoading
import com.dapascript.newscompose.ui.component.CustomSnackbar
import com.dapascript.newscompose.ui.component.TechNewsComponent

@Composable
fun TechNewsScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: TechNewsViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) = with(viewModel) {

    val listUSNews = listUSNewsState.collectAsLazyPagingItems()
    LaunchedEffect(Unit) { getUSNews() }
    TechNewsContent(listUSNews, snackbarHostState, navigateToDetail)
}

@Composable
private fun TechNewsContent(
    listUSNews: LazyPagingItems<TechNewsEntity>,
    snackbarHostState: SnackbarHostState,
    navigateToDetail: (String) -> Unit
) {
    when (listUSNews.itemCount) {
        0 -> {
            if (listUSNews.loadState.refresh == LoadState.Loading) {
                CustomLoading(Modifier.fillMaxSize())
            } else {
                CustomError { listUSNews.retry() }
            }
        }

        else -> {
            LazyColumn {
                items(
                    count = listUSNews.itemCount,
                ) { news ->
                    listUSNews[news]?.let {
                        TechNewsComponent(
                            newsEntity = it,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }

                listUSNews.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> item {
                            CustomLoading(Modifier.fillParentMaxSize())
                        }

                        loadState.refresh is LoadState.Error -> item {
                            CustomSnackbar(
                                message = "Data Not Available, Try Again Later",
                                snackbarHostState = snackbarHostState
                            )
                        }

                        loadState.append == LoadState.Loading -> item { CustomLoading(Modifier.fillMaxWidth()) }

                        loadState.append is LoadState.Error -> item {
                            CustomSnackbar(
                                message = "No more news to show",
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}