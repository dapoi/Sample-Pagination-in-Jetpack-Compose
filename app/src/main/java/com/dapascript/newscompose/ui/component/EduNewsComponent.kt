package com.dapascript.newscompose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dapascript.newscompose.data.entity.EduNewsEntity

@Composable
fun EduNewsComponent(
    newsEntity: EduNewsEntity,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .clickable { newsEntity.url?.let { navigateToDetail(it) } },
        colors = cardColors(containerColor = colorScheme.onPrimary),
        elevation = cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 10.dp),
                model = newsEntity.urlToImage.orEmpty(),
                contentDescription = null,
                contentScale = Crop,
                error = painterResource(id = android.R.drawable.ic_menu_report_image)
            )
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                Text(
                    text = newsEntity.title ?: "Title not available",
                    style = typography.bodyLarge,
                    maxLines = 2,
                    overflow = Ellipsis
                )
                Text(
                    text = newsEntity.description ?: "Description not available",
                    style = typography.bodySmall,
                    maxLines = 2,
                    overflow = Ellipsis,
                    color = Gray
                )
            }
        }
    }
}