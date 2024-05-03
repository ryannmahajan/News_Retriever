package com.ryannm.newsretriever.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ryannm.newsretriever.model.NewsItemModel

@Composable
fun NewsItem(item: NewsItemModel) {
    Text(text = item.title ?: "None", modifier = Modifier.fillMaxWidth())
}