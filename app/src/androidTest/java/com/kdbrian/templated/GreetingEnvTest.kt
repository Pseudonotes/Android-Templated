package com.kdbrian.templated

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GreetingEnvTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun greeting_should_display_env_as_name() {
        val expectedText = "Hello ${BuildConfig.ENV}!"

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }
}
