package com.kdbrian.templated.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpConfig {
        return OkHttpConfig().apply {
            addInterceptor { c ->
                val request = c.request()
                Timber.d("[${request.method}] : ${request.url}")
                c.proceed(request)
            }
        }
    }

    @Singleton
    @Provides
    fun providesJson(): Json {
        return Json { ignoreUnknownKeys = true; isLenient = true; }
    }


    @Singleton
    @Provides
    fun providesHttpClient(
        config: OkHttpConfig,
        json: Json
    ): HttpClient {
        return HttpClient(OkHttp) {
            config.config { }

            install(ContentNegotiation) {
                json(json = json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.i(message)
                    }
                }
            }
        }
    }

}