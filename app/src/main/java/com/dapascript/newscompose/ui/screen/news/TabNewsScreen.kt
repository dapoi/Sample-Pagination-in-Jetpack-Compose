package com.dapascript.newscompose.ui.screen.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dapascript.newscompose.ui.screen.BaseScreen
import com.dapascript.newscompose.ui.screen.news.education.EduNewsScreen
import com.dapascript.newscompose.ui.screen.news.tech.TechNewsScreen
import com.dapascript.newscompose.utils.TabRowItem
import kotlinx.coroutines.launch

@Composable
fun TabNewsScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    BaseScreen(
        title = "News",
        navController = navController,
        snackbarHostState = snackbarHostState,
        showIconBack = false
    ) {
        val tabsItem = listOf(
            TabRowItem(
                title = "News With Paging 3",
                content = { TechNewsScreen(snackbarHostState = snackbarHostState, navigateToDetail = navigateToDetail) }
            ),
            TabRowItem(
                title = "News Without Library",
                content = { EduNewsScreen(snackbarHostState = snackbarHostState, navigateToDetail = navigateToDetail) }
            )
        )
        val pagerState = rememberPagerState { tabsItem.size }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabsItem.forEachIndexed { index, data ->
                    Tab(
                        text = { Text(text = data.title) },
                        selected = pagerState.currentPage == index,
                        selectedContentColor = colorScheme.primary,
                        unselectedContentColor = colorScheme.secondary,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                tabsItem[page].content()
            }
        }
    }
}