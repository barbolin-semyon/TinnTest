package com.example.tinntest.data.modelForJSON

data class RegisterModel (
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val code: String
)

data class SignInModel (
    val email: String,
    val password: String,
)

data class ResponceModel(
    val success: Boolean = false,
    val message: String = "",
    val data: ResponceDataModel = ResponceDataModel()
)

data class ResponceDataModel(
    val email: Array<String> = emptyArray(),
    val password: Array<String> = emptyArray(),
    val code: Array<String> = emptyArray(),
)
