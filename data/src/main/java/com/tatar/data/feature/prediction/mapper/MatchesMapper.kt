package com.tatar.data.feature.prediction.mapper

import com.tatar.core.data.DataClassMapper
import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.data.feature.prediction.model.Match
import com.tatar.data.feature.prediction.model.Matches
import com.tatar.data.feature.prediction.model.MatchesError
import com.tatar.domain.feature.prediction.entity.MatchEntity
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity

internal object MatchesMapper :
    DataClassMapper<Matches, MatchesError, MatchesEntity, MatchesErrorEntity> {

    override fun mapToNewModel(result: Result<Matches, MatchesError>): Result<MatchesEntity, MatchesErrorEntity> {
        return when (result) {
            is SuccessResult -> SuccessResult(
                MatchesEntity(
                    matches = result.data.matches.map { toMatchEntity(it) }
                )
            )
            is ErrorResult -> ErrorResult(MatchesErrorEntity)
        }
    }

    internal fun toMatchEntity(it: Match) = MatchEntity(
        homeTeamName = it.homeTeamName,
        awayTeamName = it.awayTeamName
    )

    internal fun toMatch(it: MatchEntity) = Match(
        matchIdentifier = it.getMatchIdentifier(),
        homeTeamName = it.getNonNullHomeTeamName(),
        awayTeamName = it.getNonNullAwayTeamName()
    )
}
