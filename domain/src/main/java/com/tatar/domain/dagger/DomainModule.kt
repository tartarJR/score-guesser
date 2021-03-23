package com.tatar.domain.dagger

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
object DomainModule {

    @BackgroundThread
    @ApplicationScope
    @Provides
    fun backgroundScheduler(): Scheduler = Schedulers.io()
}
