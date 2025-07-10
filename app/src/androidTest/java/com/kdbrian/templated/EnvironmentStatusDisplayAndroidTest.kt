package com.kdbrian.templated

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EnvironmentStatusDisplayAndroidTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun environment_and_connectivity_displayed_when_connected() {
        val expectedEnv = "Environment: ${BuildConfig.ENV}"

        composeTestRule.setContent {
            EnvironmentStatusDisplay(isConnected = true)
        }

        composeTestRule.onNodeWithText("Connected").assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedEnv).assertIsDisplayed()
    }

    @Test
    fun environment_and_connectivity_displayed_when_disconnected() {
        val expectedEnv = "Environment: ${BuildConfig.ENV}"

        composeTestRule.setContent {
            EnvironmentStatusDisplay(isConnected = false)
        }

        composeTestRule.onNodeWithText("No Internet").assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedEnv).assertIsDisplayed()
    }
}
