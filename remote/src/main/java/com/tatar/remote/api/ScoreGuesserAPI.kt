package com.tatar.remote.api

import com.tatar.remote.source.prediction.model.MatchesSuccessResponse
import com.tatar.remote.source.result.model.MatchResultsSuccessResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ScoreGuesserAPI {
    @GET("786f49dd-5a8b-4d70-a5d7-de97e5c0ff5c")
    fun getMatches(): Single<MatchesSuccessResponse>

    @GET("305194d6-60e4-45ff-ad5a-d512e5287c76")
    fun getResults(): Single<MatchResultsSuccessResponse>
}

