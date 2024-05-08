package com.example.myapp.ui.viewmodels

import androidx.paging.Pager
import app.cash.turbine.test
import com.example.myapp.common.UIState
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.dispatcher.TestDispatcherProvider
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.common.networkhelper.TestNetworkHelper
import com.example.myapp.data.model.MainData
import com.example.myapp.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var mainRepository: MainRepository

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var mainPager: Pager<Int, MainData>

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        Dispatchers.setMain(dispatcherProvider.main)
    }

//    @Test
//    fun fetchData_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
//        runTest {
//            doReturn(flowOf(emptyList<MainData>()))
//                .`when`(mainRepository)
//                .getMainData(1)
//
//            val viewModel = MainViewModel(
//                mainRepository,
//                dispatcherProvider,
//                networkHelper,
//                mainPager
//            )
//            viewModel.mainItem.test {
//                assertEquals(PagingData.empty<MainData>(), awaitItem())
//                cancelAndIgnoreRemainingEvents()
//            }
//            verify(mainRepository, Mockito.times(1)).getMainData(1)
//        }
//    }

//    @Test
//    fun fetchData_whenRepositoryResponseError_shouldSetErrorUiState() {
//        runTest {
//            val errorMessage = "Error Message"
//            val exception = IllegalStateException(errorMessage)
//            doReturn(flow<List<MainData>> {
//                throw exception
//            })
//                .`when`(mainRepository)
//                .getMainData(1)
//
//            val viewModel = MainViewModel(
//                mainRepository,
//                dispatcherProvider,
//                networkHelper,
//                mainPager
//            )
//            viewModel.mainItem.test {
//                assertEquals(
//                    UIState.Failure(exception, null).toString(),
//                    awaitItem().toString()
//                )
//                cancelAndIgnoreRemainingEvents()
//            }
//            verify(mainRepository, Mockito.times(1)).getMainData(1)
//        }
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}