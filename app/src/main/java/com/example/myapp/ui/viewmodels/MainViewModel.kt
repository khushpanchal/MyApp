package com.example.myapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.data.repository.MainRepository
import com.example.myapp.data.model.MainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val pager: Pager<Int, MainData>,
): ViewModel() {

    private val _mainItem = MutableStateFlow<PagingData<MainData>>(PagingData.empty())
    val mainItem: StateFlow<PagingData<MainData>> = _mainItem
//    private val _mainItem = MutableStateFlow<UIState<List<MainData>>>(UIState.Empty)
//    val mainItem: StateFlow<UIState<List<MainData>>> = _mainItem

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            pager.flow.cachedIn(viewModelScope).collect {
                _mainItem.emit(it)
            }
//            _mainItem.emit(UIState.Loading)
//            if(!networkHelper.isNetworkConnected()) {
//                mainRepository
//                    .getDataFromDb()
//                    .flowOn(dispatcherProvider.io)
//                    .catch {
//                        _mainItem.emit(UIState.Failure(it))
//                    }
//                    .collect {
//                        _mainItem.emit(UIState.Success(it))
//                    }
//                return@launch
//            }
//
//            mainRepository
//                .getMainData()
//                .flowOn(dispatcherProvider.io)
//                .catch {
//                    _mainItem.emit(UIState.Failure(it))
//                }
//                .collect {
//                    _mainItem.emit(UIState.Success(it))
//                }
        }
    }

}