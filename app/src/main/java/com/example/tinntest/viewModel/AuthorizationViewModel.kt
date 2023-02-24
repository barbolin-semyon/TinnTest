package com.example.tinntest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinntest.data.modelForJSON.*
import com.example.tinntest.data.networkService.AuthorizationService
import com.example.tinntest.data.networkService.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                if (response.body() != null) {
                    _token.value = response.body()!!.data.token
                } else {
                    ErrorObserver.showErrorMessage("Пользователь не авторизован")
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
                        if (response.body() != null) {
                            _token.value = response.body()!!.data.token
                        } else {
                            val gson = Gson()
                            val type = object : TypeToken<ResponceModel>() {}.type
                            val errorResponse: ResponceModel? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)

                            val data = errorResponse!!.data
                            showArrayError(data.email)
                            showArrayError(data.password)
                            showArrayError(data.code)
                        }
                    }

                override fun onFailure(call: Call<ResponceModel>, t: Throwable) {
                    ErrorObserver.showErrorMessage(t.message.toString())
                }
            })
    }

    fun verificationEmail(code: String, token: String) = viewModelScope.launch {
        db.verificationEmail(VerificationModel(code), "Bearer $token")
            .enqueue(object : Callback<ResponceVerificationModel> {
                override fun onResponse(
                    call: Call<ResponceVerificationModel>,
                    response: Response<ResponceVerificationModel>
                ) {
                    response.body()?.let {
                        if (it.getStatus()) _emailIsVerificated.value = it.getStatus()
                        else ErrorObserver.showErrorMessage("Код не верен")
                    }
                }

                override fun onFailure(call: Call<ResponceVerificationModel>, t: Throwable) {
                    ErrorObserver.showErrorMessage(t.message.toString())
                }

            })
    }
}