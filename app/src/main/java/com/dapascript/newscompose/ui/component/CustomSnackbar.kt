package com.dapascript.newscompose.ui.component

import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
) {
    val coroutineScope = rememberCoroutineScope()

    // LaunchedEffect will run the effect once when the composable is first drawn
    LaunchedEffect(true) {
        // Launch a coroutine to show the snackbar on the main thread
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = Short,
            )
        }
    }
}