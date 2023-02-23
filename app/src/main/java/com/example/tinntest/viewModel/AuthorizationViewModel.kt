package com.example.tinntest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinntest.data.modelForJSON.RegisterModel
import com.example.tinntest.data.modelForJSON.ResponceModel
import com.example.tinntest.data.modelForJSON.SignInModel
import com.example.tinntest.data.networkService.AuthorizationService
import com.example.tinntest.data.networkService.RetrofitClient
import retrofit2.Callback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class AuthorizationViewModel : ViewModel() {
    private val _token = MutableLiveData("")
    val token: LiveData<String>
        get() = _token

    private val db = RetrofitClient.getRetrofitService().create(AuthorizationService::class.java)

    private fun showArrayError(array: Array<String>) {
        array.forEach { ErrorObserver.showErrorMessage(it) }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        db.login(SignInModel(email, password)).enqueue(object : Callback<ResponceModel> {
            override fun onResponse(call: Call<ResponceModel>, response: Response<ResponceModel>) {
                response.body()?.let { body ->
                    if (body.success) {
                        _token.value = body.data.token
                    } else {
                        val data = body.data
                        showArrayError(data.email)
                        showArrayError(data.password)
                    }
                }
            }

            override fun onFailure(call: Call<ResponceModel>, t: Throwable) {
                ErrorObserver.showErrorMessage(t.message.toString())
            }

        })
    }
    fun register(email: String, password: String, code: String) = viewModelScope.launch {
        db.register(RegisterModel(email, password, password, code))
            .enqueue(object : Callback<ResponceModel> {
                override fun onResponse(
                    call: Call<ResponceModel>,
                    response: Response<ResponceModel>
                ) {
                    response.body()?.let { body ->
                        if (body.success) {
                            _token.value = body.data.token
                        } else {
                            val data = body.data
                            showArrayError(data.email)
                            showArrayError(data.password)
                            showArrayError(data.code)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponceModel>, t: Throwable) {
                    ErrorObserver.showErrorMessage(t.message.toString())
                }
            })
    }
}