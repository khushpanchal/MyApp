package com.example.myapp.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.data.model.MainData
import com.example.myapp.data.repository.MainRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainPagingSource @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
): PagingSource<Int, MainData>() {

    override fun getRefreshKey(state: PagingState<Int, MainData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainData> {
        val page = params.key ?: 1
        lateinit var loadResult: LoadResult<Int, MainData>

        withContext(dispatcherProvider.io) {
            kotlin.runCatching {
                if (!networkHelper.isNetworkConnected()) {
                    if (page == 1) {
                        val mainData = mainRepository.getDataFromDb()
                        loadResult = LoadResult.Page(
                            data = mainData,
                            prevKey = page.minus(1),
                            nextKey = if (mainData.isEmpty()) null else page.plus(1)
                        )
                    } else {
                        throw RuntimeException("No Internet")
                    }
                } else {
                    val mainData = mainRepository.getMainData(page)
                    loadResult = LoadResult.Page(
                        data = mainData,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (mainData.isEmpty()) null else page.plus(1)
                    )
                }
            }.onFailure {
                loadResult = LoadResult.Error(it)
            }
        }
        return loadResult
    }

}