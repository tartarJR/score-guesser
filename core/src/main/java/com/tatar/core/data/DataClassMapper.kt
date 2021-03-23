package com.tatar.core.data

import io.reactivex.rxjava3.core.Single

interface DataClassMapper<SuccessEntity, ErrorEntity, SuccessModel, ErrorModel> {
    fun mapToNewModel(result: Result<SuccessEntity, ErrorEntity>): Result<SuccessModel, ErrorModel>
}

fun <SuccessEntity, ErrorEntity, SuccessModel, ErrorModel> Single<Result<SuccessEntity, ErrorEntity>>.toNewModel(
    dataClassMapper: DataClassMapper<SuccessEntity, ErrorEntity, SuccessModel, ErrorModel>
): Single<Result<SuccessModel, ErrorModel>> = this.map { dataClassMapper.mapToNewModel(it) }
