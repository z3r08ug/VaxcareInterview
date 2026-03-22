package com.z3r08ug.vaxcareinterview.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import com.z3r08ug.vaxcareinterview.data.remote.BookService
import com.z3r08ug.vaxcareinterview.data.remote.BookServiceImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                val json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
                json(json, ContentType.Application.Json)
                json(json, ContentType.Text.Plain)
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideBookService(httpClient: HttpClient): BookService {
        return BookServiceImpl(httpClient)
    }
}
