package com.example.khizana.utilis

sealed class Response {

    data object Loading : Response()
    data class Success<T>(var result: T?) : Response()
    data class Failure(var exception: String) : Response()

}