package com.skillbox.generics

sealed class Result <out T, R> {

    data class Success<T, R>(val successfulResult: T): Result<T,R>()

    data class Error<T, R>(val errorResult: R): Result<T,R>()

    fun returnResult(a: Int): Result<Any, String>{
        val success: Result<Number, String> = Success(0)
        val error: Result<Int, String> = Error("error")
        return if (a > 0) success else error
    }


}