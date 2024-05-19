package com.example.myapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.common.UIState
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.data.model.MainData
import com.example.myapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _mainItem = MutableStateFlow<UIState<List<MainData>>>(UIState.Empty)
    val mainItem: StateFlow<UIState<List<MainData>>> = _mainItem

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _mainItem.emit(UIState.Loading)
            if (!networkHelper.isNetworkConnected()) {
                mainRepository
                    .getDataFromDb()
                    .flowOn(dispatcherProvider.io)
                    .catch {
                        _mainItem.emit(UIState.Failure(it))
                    }
                    .collect {
                        _mainItem.emit(UIState.Success(it))
                    }
                return@launch
            }

            mainRepository
                .getMainData()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _mainItem.emit(UIState.Failure(it))
                }
                .collect {
                    _mainItem.emit(UIState.Success(it))
                }
        }
    }

}

//For search

/*
    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query
                .debounce(500)
                .filter {
                    return@filter it.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { searchQuery ->
                    _searchItem.emit(UIState.Loading)
                    mainRepository.getMainData(searchQuery = searchQuery)
                        .catch {
                            _searchItem.emit(UIState.Failure(it))
                        }
                }
                .flowOn(dispatcherProvider.io)
                .collect {
                    _searchItem.emit(UIState.Success(it))
                }
        }
    }

    fun searchLocation(searchQuery: String) {
        viewModelScope.launch {
            query.value = searchQuery
        }
    }
*/
