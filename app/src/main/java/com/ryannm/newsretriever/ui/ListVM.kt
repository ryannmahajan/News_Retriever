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

    init {
        viewModelScope.launch {
            _newsItemModels.value = NewsRepository.get()
            _loading.value = false
        }
    }
}