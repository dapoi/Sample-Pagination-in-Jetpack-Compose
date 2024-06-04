package com.dapascript.newscompose.utils

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    val content: @Composable () -> Unit
)