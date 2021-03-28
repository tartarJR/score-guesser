package com.tatar.domain.feature.main

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.main.entity.LastScreenEntity
import com.tatar.domain.repository.SettingsRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLastScreenUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val settingsRepository: SettingsRepository,
) : SingleUseCase<Unit, LastScreenEntity>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Single<LastScreenEntity> {
        return settingsRepository.getLastScreen()
            .map { LastScreenEntity(it) }
    }
}
