package com.tatar.domain.base

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler

abstract class CompletableUseCase<in Params>(
    @MainThread private val mainScheduler: Scheduler,
    @BackgroundThread private val backgroundScheduler: Scheduler
) {
    protected abstract fun execute(params: Params): Completable

    operator fun invoke(params: Params): Completable {
        return execute(params)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
    }
}

operator fun CompletableUseCase<Unit>.invoke(): Completable = this(Unit)
