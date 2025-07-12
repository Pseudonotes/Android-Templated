package com.kdbrian.templated.data.remote.service

interface ExampleDotComService {
    suspend fun exampleDotCom(): Result<String>
}