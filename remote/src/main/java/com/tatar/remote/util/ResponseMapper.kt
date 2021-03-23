package com.tatar.remote.util

import com.tatar.core.data.Result
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException

internal interface ResponseMapper<R, SR : R, FR : R, SuccessData, ErrorData> {
    fun mapToDataModel(result: R): Result<SuccessData, ErrorData>
}

internal fun <R, SR : R, FR : R, SuccessData, ErrorData> Single<R>.toDataResult(
    response: ResponseMapper<R, SR, FR, SuccessData, ErrorData>
): Single<Result<SuccessData, ErrorData>> = this.map { response.mapToDataModel(it) }

inline fun <reified T : R, R> Single<out R>.mapError(): Single<R> =
    this.map { it }
        .onErrorResumeNext { error ->
            if (error is HttpException && error.code() >= 400) {
                val primaryConstructor = T::class.constructors.find { it.parameters.isEmpty() }
                Single.just(primaryConstructor?.call())
            } else {
                Single.error(error)
            }
        }
