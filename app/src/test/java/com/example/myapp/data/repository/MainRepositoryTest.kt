package com.example.myapp.data.repository

import com.example.myapp.data.database.DatabaseService
import com.example.myapp.data.model.MainData
import com.example.myapp.data.model.News
import com.example.myapp.data.network.FakeNetworkService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    @Mock
    private lateinit var fakeNetworkService: FakeNetworkService

    @Mock
    private lateinit var database: DatabaseService

    private lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        mainRepository = MainRepository(fakeNetworkService, database)
    }

    @Test
    fun getNews_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val data = MainData(
                title = "title",
                urlToImage = "urlToImage"
            )

            val mainDataList = mutableListOf<MainData>()
            mainDataList.add(data)

            val response = News(
                status = "ok", totalResults = 1, articles = mainDataList
            )

            Mockito.doReturn(response).`when`(fakeNetworkService).getMainData()
            Mockito.doReturn(
                flowOf(
                    response.articles
                )
            )
                .`when`(database).getAllData()

            val actual = mainRepository.getMainData().first()
            assertEquals(response.articles, actual)

        }
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message"

//            Mockito.doThrow(RuntimeException(errorMessage)).`when`(fakeNetworkService).getMainData() //For network failure if not handled
            Mockito.doThrow(RuntimeException(errorMessage)).`when`(database).getAllData()

            assertThrows(RuntimeException::class.java) {
                runBlocking {
                    mainRepository.getMainData().collect {

                    }
                }
            }
        }
    }
}