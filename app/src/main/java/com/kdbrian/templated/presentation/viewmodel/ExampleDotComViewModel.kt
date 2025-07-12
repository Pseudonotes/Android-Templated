package com.kdbrian.templated.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdbrian.templated.data.remote.service.ExampleDotComService
import com.kdbrian.templated.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val exampleDotCom = "exampleDotCom"
const val isError = "isError"

@HiltViewModel
class ExampleDotComViewModel @Inject constructor(
    private val exampleDotComService: ExampleDotComService,
) : ViewModel() {

    private val _result: MutableStateFlow<Resource<String>> = MutableStateFlow(Resource.Noting())
    val result: StateFlow<Resource<String>>
        get() = _result.asStateFlow()

    init {
        exampleDotCom()
    }


    fun exampleDotCom() {
        viewModelScope.launch {

            _result.emit(
                exampleDotComService.exampleDotCom().fold(
                    onSuccess = {
                        Timber.tag("onSuccess").d(it)
                        Resource.Success(it)
                    },
                    onFailure = {
                        Timber.tag("onFailure").d(it)
                        Resource.Error(it.message.toString())
                    }
                )
            )
        }
    }
}