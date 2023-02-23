package com.example.tinntest.data.networkService

import com.example.tinntest.data.modelForJSON.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthorizationService {
    @POST("register")
    fun register(@Body registerModel: RegisterModel): Call<ResponceModel>

    @POST("login")
    fun login(@Body signInModel: SignInModel): Call<ResponceModel>

    @POST("verification/email")
    fun verificationEmail(
        @Body verificationModel: VerificationModel,
        @Header("Authorization") token: String
    ): Call<ResponceVerificationModel>
}