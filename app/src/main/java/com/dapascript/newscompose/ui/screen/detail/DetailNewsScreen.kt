package com.dapascript.newscompose.ui.screen.detail

import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.dapascript.newscompose.ui.component.CustomLoading
import com.dapascript.newscompose.ui.screen.BaseScreen

@Composable
fun DetailNewsScreen(
    url: String,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    BaseScreen(
        title = "Detail News",
        navController = navController,
        snackbarHostState = snackbarHostState
    ) {
        var hasLoaded by remember { mutableStateOf(false) }

        AndroidView(
            factory = {
                WebView(it).apply {
                    this.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    this.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) =
                            request?.url != null

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            hasLoaded = (view?.progress ?: 0) >= 50
                        }
                    }
                }
            },
            update = { it.loadUrl(url) }
        )

        if (hasLoaded.not()) CustomLoading(Modifier.fillMaxSize())
    }
}