package com.ryannm.newsretriever.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryannm.newsretriever.model.NewsItemModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NewsItem(item: NewsItemModel, onClick: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 8.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.title ?: "Couldn't load Title",
            style = MaterialTheme.typography.titleLarge)
//        Text(text = item.description ?: "Couldn't load Description",
//            style = MaterialTheme.typography.bodyMedium)
//        Text(text = item.author ?: "Couldn't load Author",
//            style = MaterialTheme.typography.labelSmall)
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.padding(end = 4.dp),
                text = item.source?.name ?: "Couldn't load Source",
                style = MaterialTheme.typography.labelSmall)
            Text(modifier = Modifier.padding(end = 4.dp),
                text = "·")
            Text(text = item.publishedAt?.let { convertDate(it) } ?: "Couldn't load Date",
                style = MaterialTheme.typography.labelSmall)
        }

    }
}

fun convertDate(date: Date): String {
    return SimpleDateFormat("MMM dd, hh:mm a", Locale.ENGLISH).format(date)
}

@Preview
@Composable
fun ExampleNewsItem() {
    NewsItem(item = NewsItemModel(), {})
}