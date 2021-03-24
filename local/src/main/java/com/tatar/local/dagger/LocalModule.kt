package com.tatar.local.dagger

import android.content.Context
import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.data.source.LocalDataSource
import com.tatar.local.LocalDataSourceImpl
import com.tatar.local.room.ScoreGuesserDB
import com.tatar.local.room.feature.prediction.dao.PredictionsDao
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [LocalModule.Binder::class])
object LocalModule {

    @Module
    interface Binder {
        @Binds
        fun bindLocalDataSourceImpl(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
    }

    @ApplicationScope
    @Provides
    internal fun provideScoreGuesserDB(applicationContext: Context): ScoreGuesserDB {
        return ScoreGuesserDB.getInstance(applicationContext)
    }

    @ApplicationScope
    @Provides
    internal fun providePredictionsDao(scoreGuesserDB: ScoreGuesserDB): PredictionsDao {
        return scoreGuesserDB.predictionsDao
    }
}
