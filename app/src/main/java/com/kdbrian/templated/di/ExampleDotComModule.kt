package com.kdbrian.templated.di

import com.kdbrian.templated.data.remote.repo.ExampleDotComRepoImpl
import com.kdbrian.templated.data.remote.service.ExampleDotComService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExampleDotComModule {

    @Singleton
    @Provides
    fun providesExampleDotComService(
        client: HttpClient
    ): ExampleDotComService {
        return ExampleDotComRepoImpl(
            client = client
        )
    }

}