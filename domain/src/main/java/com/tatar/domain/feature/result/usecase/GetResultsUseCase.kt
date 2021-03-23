package com.tatar.domain.feature.result.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.data.Result
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity
import com.tatar.domain.feature.result.repository.ResultRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetResultsUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val resultRepository: ResultRepository
) : SingleUseCase<Unit, Result<MatchResultsEntity, MatchResultsErrorEntity>>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Single<Result<MatchResultsEntity, MatchResultsErrorEntity>> {
        return resultRepository.getMatchResults()
    }
}
