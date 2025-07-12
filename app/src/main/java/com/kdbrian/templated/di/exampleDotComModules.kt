package com.kdbrian.templated.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber

val exampleDotComModules = module {

    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<HttpClient> {
        HttpClient(OkHttp) {
            engine {
                addInterceptor { chain ->
                    var req = chain.request()
                    Timber.d("[${req.method}] ${req.url}")
                    chain.proceed(req)
                }
            }

            install(ContentNegotiation) {
                json(
                    get<Json>()
                )
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