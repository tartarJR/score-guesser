package com.tatar.domain.base

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

abstract class SingleUseCase<in Params, Output>(
    @MainThread private val mainScheduler: Scheduler,
    @BackgroundThread private val backgroundScheduler: Scheduler
) {
    protected abstract fun execute(params: Params): Single<Output>

    operator fun invoke(params: Params): Single<Output> {
        return execute(params)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
    }
}

operator fun <Output> SingleUseCase<Unit, Output>.invoke(): Single<Output> = this(Unit)
