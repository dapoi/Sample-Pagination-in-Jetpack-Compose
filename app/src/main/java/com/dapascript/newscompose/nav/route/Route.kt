package com.dapascript.newscompose.nav.route

import kotlinx.serialization.Serializable

@Serializable
object NewsScreenRoute

@Serializable
data class DetailNewsScreenRoute(val url: String)