package com.example.tinntest.utils

fun String.emailIfValid(): Boolean {
    return (this.isEmpty() || this.matches(Regex("\\S*@\\S*[.]\\S*")))
}