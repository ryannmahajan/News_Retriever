@file:OptIn(ExperimentalMaterial3Api::class)

package com.ryannm.newsretriever.ui

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryannm.newsretriever.R

@Composable
fun ListScreen(
    listVM: ListVM = viewModel()
) {
    val newsItemModels by listVM.newsItemModel.collectAsStateWithLifecycle()
    val loading by listVM.loading.collectAsStateWithLifecycle()
    val ascending by listVM.ascending.collectAsStateWithLifecycle()

    val context = LocalContext.current
    
    Scaffold (
        topBar = {
            Surface (
                shadowElevation = 2.dp
            ) {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        var showMenu by remember { mutableStateOf(false) }
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(painterResource(R.drawable.baseline_sort_24), "sort", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            Text(modifier = Modifier.fillMaxWidth().padding(8.dp),
                                text= "Sort by Date",
                                textAlign = TextAlign.Center)
                            val setAscending = { b:Boolean ->
                                listVM.setAscending(b)
                            }

                            val values = mapOf(
                                true to "Ascending",
                                false to "Descending"
                            )

                            values.forEach {
                                val boolean = it.key; val label = it.value
                                DropdownMenuItem(text = {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(boolean==ascending, onClick = {setAscending(boolean)})
                                        Text(label)
                                    }
                                }, onClick = {
                                    setAscending(boolean)
                                })
                            }
                        }
                    }
                )
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (loading) {
                CircularProgressIndicator()
            } else {
                LazyColumn() {
                    itemsIndexed(newsItemModels) { index, newsItemModel ->
                        NewsItem(item = newsItemModel) {
                            newsItemModel.url ?.let {
                                val intent = CustomTabsIntent.Builder()
                                    .build()
                                intent.launchUrl(context, Uri.parse(it))
                            } ?: Toast.makeText(context, "Can't Load URL", Toast.LENGTH_SHORT).show()
                        }
                        if (index < newsItemModels.lastIndex)
                            Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}