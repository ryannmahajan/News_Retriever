package com.ryannm.newsretriever.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryannm.newsretriever.model.NewsItemModel
import com.ryannm.newsretriever.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListVM(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val _newsItemModels = MutableStateFlow( listOf<NewsItemModel>())
    val newsItemModel = _newsItemModels.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _ascending = MutableStateFlow<Boolean?>(null)
    val ascending = _ascending.asStateFlow()

    init {
        changeNewsItemsBy { NewsRepository.getNewsArticles() }
    }

    private fun changeNewsItemsBy(fn: suspend (List<NewsItemModel>)->List<NewsItemModel>) {
        viewModelScope.launch {
            _loading.value = true
            _newsItemModels.value = fn(_newsItemModels.value)
            _loading.value = false
        }
    }

    fun setAscending(b: Boolean) {
        if (b==ascending.value) return
        _ascending.value = b

        changeNewsItemsBy { list ->
            if (b) list.sortedBy { it.publishedAt }
            else list.sortedByDescending { it.publishedAt }
        }
    }

}