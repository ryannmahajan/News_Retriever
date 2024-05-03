@file:OptIn(ExperimentalMaterial3Api::class)

package com.ryannm.newsretriever.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryannm.newsretriever.R

@Composable
fun ListScreen(
    listVM: ListVM = viewModel()
) {
    val newsItemModels by listVM.newsItemModel.collectAsStateWithLifecycle()
    val loading by listVM.loading.collectAsStateWithLifecycle()
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        }
    ) { padding ->
        if (loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(newsItemModels) { newsItemModel ->
                    NewsItem(item = newsItemModel)
                }
            }
        }
    }
}