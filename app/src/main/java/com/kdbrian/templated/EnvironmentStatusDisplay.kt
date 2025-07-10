package com.kdbrian.templated

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun EnvironmentStatusDisplay(
    isConnected: Boolean,
    env: String = BuildConfig.ENV,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isConnected) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        ) {
            Text(
                text = if (isConnected) "Connected" else "No Internet",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White
            )
        }

        Card(
            modifier = modifier.align(Alignment.Center),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Environment: $env",
                    style = MaterialTheme.typography.displayMedium.copy(
                        textAlign = TextAlign.Center
                    )
//                    modifier = Modifier.padding(16.dp)
                )


                Text(
                    text = BuildConfig.VERSION_NAME,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview(name = "Connected - DEV")
@Composable
fun PreviewConnectedDev() {
    EnvironmentStatusDisplay(isConnected = true, env = "dev")
}

@Preview(name = "Disconnected - STAGING")
@Composable
fun PreviewDisconnectedStaging() {
    EnvironmentStatusDisplay(isConnected = false, env = "staging")
}

@Preview(name = "Connected - PROD")
@Composable
fun PreviewConnectedProd() {
    EnvironmentStatusDisplay(isConnected = true, env = "prod")
}


@Composable
fun rememberIsConnected(): Boolean {
    val context = LocalContext.current
    val connectivityManager = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    val networkState =
        rememberUpdatedState(newValue = connectivityManager.activeNetworkInfo?.isConnected == true)

    // For demo purposes; for production use NetworkCallback instead.
    return networkState.value
}
