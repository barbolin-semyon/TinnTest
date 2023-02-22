package com.example.tinntest.data.networkService

import com.example.tinntest.data.modelForJSON.RegisterModel
import com.example.tinntest.data.modelForJSON.ResponceDataModel
import com.example.tinntest.data.modelForJSON.ResponceModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {
    @POST("/register")
    fun register(@Body registerModel: RegisterModel): Call<ResponceModel>

    @POST("/login")
    fun login(@Body loginModel: RegisterModel): Call<ResponceModel>
}