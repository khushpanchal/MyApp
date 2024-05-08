package com.example.myapp.ui.paging

import androidx.paging.PagingSource
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.dispatcher.TestDispatcherProvider
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.common.networkhelper.TestNetworkHelper
import com.example.myapp.data.model.MainData
import com.example.myapp.data.repository.MainRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPagingSourceTest {

    @Mock
    private lateinit var mainRepository: MainRepository

    private lateinit var pagingSource: MainPagingSource
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        pagingSource = MainPagingSource(mainRepository, networkHelper, dispatcherProvider)
    }

    @Test
    fun load_whenParamCorrect_shouldGiveResult() {
        runTest {
            // Given
            val page = 1
            val mainData = emptyList<MainData>()

            doReturn(mainData)
                .`when`(mainRepository)
                .getMainData(page)

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, true))

            // Then
            val expected = PagingSource.LoadResult.Page(
                data = mainData,
                prevKey = null,
                nextKey = null
            )

            assertEquals(expected, result)
        }
    }

    @Test
    fun load_whenResponseFailed_shouldGiveError() {
        runTest {
            // Given
            val page = 1
            val error = RuntimeException("Fake error")
            doThrow(error)
                .`when`(mainRepository)
                .getMainData(page)

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, false))

            // Then
            val expected = PagingSource.LoadResult.Error<Int, MainData>(error)
            assertEquals(expected.toString(), result.toString())
        }
    }

}