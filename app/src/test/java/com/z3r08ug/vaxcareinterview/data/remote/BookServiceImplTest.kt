package com.z3r08ug.vaxcareinterview.data.remote

import com.z3r08ug.vaxcareinterview.data.model.BookDto
import com.z3r08ug.vaxcareinterview.data.model.BookStatusDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class BookServiceImplTest {

    private fun createHttpClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                }
                json(json, ContentType.Application.Json)
                json(json, ContentType.Text.Plain)
            }
        }
    }

    @Test
    fun `getBooks should return list of books when response is success`() = runTest {
        val books = listOf(
            BookDto(1, "Title 1", "Author 1", BookStatusDto(1, "OnShelf"), 1.0, ""),
        )
        val responseJson = Json.encodeToString(books)
        
        val engine = MockEngine { _ ->
            respond(
                content = responseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        
        val bookService = BookServiceImpl(createHttpClient(engine))
        
        val result = bookService.getBooks()
        
        assertEquals(1, result.size)
        assertEquals("Title 1", result[0].title)
    }
}
