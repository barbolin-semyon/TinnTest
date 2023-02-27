package com.example.tinntest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ErrorObserver {
    private val _errorMessage = MutableLiveData<EventError>(null)
    val errorMessage: LiveData<EventError>
        get() = _errorMessage

    fun showErrorMessage(message: String) {
        _errorMessage.value = EventError(message)
    }
}

class EventError(private val message: String) {
    fun getMessage() = message
}