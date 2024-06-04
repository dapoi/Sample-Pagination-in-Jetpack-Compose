package com.dapascript.newscompose.nav

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dapascript.newscompose.nav.route.DetailNewsScreenRoute
import com.dapascript.newscompose.nav.route.NewsScreenRoute
import com.dapascript.newscompose.ui.screen.detail.DetailNewsScreen
import com.dapascript.newscompose.ui.screen.news.TabNewsScreen

@Composable
fun NewsNavGraph() {
    val navController: NavHostController = rememberNavController()
    val snackHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = NewsScreenRoute
    ) {
        composable<NewsScreenRoute> {
            TabNewsScreen(
                snackbarHostState = snackHostState,
                navController = navController,
                navigateToDetail = { navController.navigate(DetailNewsScreenRoute(it)) }
            )
        }
        composable<DetailNewsScreenRoute> {
            val args = it.toRoute<DetailNewsScreenRoute>()
            DetailNewsScreen(url = args.url, navController = navController, snackbarHostState = snackHostState)
        }
    }
}