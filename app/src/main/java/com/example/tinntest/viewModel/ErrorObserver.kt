package com.example.tinntest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ErrorObserver {
    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun showErrorMessage(message: String) {
        _errorMessage.value = message
    }
}