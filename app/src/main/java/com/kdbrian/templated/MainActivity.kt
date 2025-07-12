package com.kdbrian.templated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kdbrian.templated.data.remote.service.ExampleDotComService
import com.kdbrian.templated.presentation.ui.theme.TemplatedTheme
import com.kdbrian.templated.presentation.util.Resource
import com.kdbrian.templated.presentation.viewmodel.ExampleDotComViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val exampleDotComViewModel by viewModels<ExampleDotComViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemplatedTheme {

                val exampleDotComResult by exampleDotComViewModel.result.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(exampleDotComResult) {
                    if (exampleDotComResult is Resource.Error) {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            exampleDotComResult.message.toString(),
                            "RETRY"
                        )

                        if (snackbarResult == SnackbarResult.ActionPerformed){
                            exampleDotComViewModel.exampleDotCom()
                        }
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        if (exampleDotComResult is Resource.Success && exampleDotComResult.data?.isNotEmpty() == true) {
                            Greeting(
                                name = exampleDotComResult.data.toString(),
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TemplatedTheme {
        Greeting("Android")
    }
}