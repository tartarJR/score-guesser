package com.tatar.domain.feature.prediction.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.data.Result
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.domain.feature.prediction.repository.MatchRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMatchesUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val matchRepository: MatchRepository,
) : SingleUseCase<Unit, Result<MatchesEntity, MatchesErrorEntity>>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return matchRepository.getMatches()
    }
}
