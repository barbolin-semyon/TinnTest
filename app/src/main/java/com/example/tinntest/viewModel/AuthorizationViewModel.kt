package com.example.tinntest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinntest.data.modelForJSON.*
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

    private val _emailIsVerificated = MutableLiveData(false)
    val emailIsVerificated: LiveData<Boolean>
        get() = _emailIsVerificated

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

    fun verificationEmail(code: String) = viewModelScope.launch {
        db.verificationEmail(VerificationModel(code))
            .enqueue(object : Callback<ResponceVerificationModel> {
                override fun onResponse(
                    call: Call<ResponceVerificationModel>,
                    response: Response<ResponceVerificationModel>
                ) {
                    response.body()?.let {
                        _emailIsVerificated.value = it.getStatus()
                    }
                }

                override fun onFailure(call: Call<ResponceVerificationModel>, t: Throwable) {
                    ErrorObserver.showErrorMessage(t.message.toString())
                }

            })
    }
}