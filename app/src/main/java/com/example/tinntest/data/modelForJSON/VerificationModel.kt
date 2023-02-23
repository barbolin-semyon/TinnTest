package com.example.tinntest.data.modelForJSON

data class VerificationModel(
    val verification_code: String
)

data class ResponceVerificationModel(
    private val status: String = ""
) {
    fun getStatus(): Boolean {
        return status == "true"
    }
}