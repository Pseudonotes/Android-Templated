package com.kdbrian.templated.data.remote.repo

import com.kdbrian.templated.data.remote.util.Endpoints
import com.kdbrian.templated.domain.repo.ExampleDotComRepo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ExampleDotComRepoImpl @Inject constructor(
    private val client: HttpClient
) : ExampleDotComRepo() {
    override suspend fun exampleDotCom(): Result<String> {
        return try {
            val response = client.get(Endpoints.exampleDotCom)
            if (response.status.value in 200..299) {
                Result.success("Example Domain")
            } else {
                Result.failure(Exception("Failed to contact domain"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}