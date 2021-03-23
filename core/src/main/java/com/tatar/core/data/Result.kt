package com.tatar.core.data

sealed class Result<Success, Error>
data class SuccessResult<Success, Error>(val data: Success) : Result<Success, Error>()
data class ErrorResult<Success, Error>(val data: Error) : Result<Success, Error>()
