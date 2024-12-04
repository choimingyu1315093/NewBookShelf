package com.example.newbookshelf.data.api

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AladinApiServiceTest {
    private lateinit var service: AladinApiService
    private lateinit var server: MockWebServer

    @Before
    fun setup(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AladinApiService::class.java)
    }

    private fun enqueueMockResponse(filename: String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getWeekBestsellerTest() = runBlocking {
        enqueueMockResponse("response.json")
        val responseBody = service.weekBestseller("ttbthdwhddnd4271541001", "Bestseller", "10", 1, "Book", "js", "20131101", "Big", 0).body()
        val request = server.takeRequest()
        Truth.assertThat(responseBody).isNotNull()
        Truth.assertThat(request.path).isEqualTo("/ItemList.aspx?ttbkey=ttbthdwhddnd4271541001&QueryType=Bestseller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101&Cover=Big&CategoryId=0")
    }

    @After
    fun tearDown(){
        server.shutdown()
    }
}