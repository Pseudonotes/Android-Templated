package com.kdbrian.templated

import org.junit.Test
import org.junit.Assert.assertTrue
import com.kdbrian.templated.BuildConfig

class EnvironmentConfigTest {

    @Test
    fun `BuildConfig ENV should be dev, staging, or prod`() {
        val allowedEnvs = setOf("dev", "staging", "prod")
        val currentEnv = BuildConfig.ENV
        println("Current ENV: $currentEnv")
        assertTrue("Unexpected ENV: $currentEnv", currentEnv in allowedEnvs)
    }
}
