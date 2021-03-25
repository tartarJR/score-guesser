package com.tatar.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tatar.local.room.ScoreGuesserDB
import com.tatar.local.room.feature.prediction.dao.PredictionsDao
import com.tatar.local.room.feature.prediction.entity.PredictionRoomEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PredictionsDaoTest {

    private lateinit var scoreGuesserDB: ScoreGuesserDB
    private lateinit var predictionsDao: PredictionsDao

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        scoreGuesserDB = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ScoreGuesserDB::class.java
        )
            .allowMainThreadQueries()
            .build()

        predictionsDao = scoreGuesserDB.predictionsDao
    }

    @Test
    fun getPredictionsWhenNoPredictionsInserted() {
        predictionsDao
            .getPredictions()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun insertAndGetPredictions() {
        val entityToBeInserted = getTestEntity()

        predictionsDao
            .insert(entityToBeInserted)
            .blockingAwait()

        predictionsDao
            .getPrediction(DEFAULT_IDENTIFIER)
            .test()
            .assertValue { entity ->
                entity.matchIdentifier == entityToBeInserted.matchIdentifier
            }
    }

    @Test
    fun updateAndGetPredictions() {
        val entityToBeInserted = getTestEntity()

        predictionsDao
            .insert(entityToBeInserted)
            .blockingAwait()

        val entityToBeUpdated = PredictionRoomEntity(
            matchIdentifier = DEFAULT_IDENTIFIER,
            homeTeamName = "u_homeTeamName",
            awayTeamName = "u_awayTeamName",
            homeScorePrediction = "u_homeScorePrediction",
            awayScorePrediction = "u_awayScorePrediction"
        )
        predictionsDao.insert(entityToBeUpdated).blockingAwait()

        predictionsDao
            .getPrediction(DEFAULT_IDENTIFIER)
            .test()
            .assertValue { entity ->
                entity.matchIdentifier == entityToBeInserted.matchIdentifier
            }
    }

    @Test
    fun deleteAndGetPredictions() {
        val entityToBeInserted = getTestEntity()

        predictionsDao.insert(entityToBeInserted).blockingAwait()

        predictionsDao
            .getPrediction(DEFAULT_IDENTIFIER)
            .subscribe { entity ->
                predictionsDao.delete(entity).blockingAwait()
            }

        predictionsDao
            .getPrediction(DEFAULT_IDENTIFIER)
            .test()
            .assertNoValues()
    }

    @Test
    fun deleteAllAndGetPredictions() {
        val entityToBeInserted = getTestEntity()

        predictionsDao.insert(entityToBeInserted).blockingAwait()

        scoreGuesserDB.clearAllTables()

        predictionsDao
            .getPredictions()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun insertIdenticalAndGetPredictions() {
        val entityToBeInserted = getTestEntity()

        predictionsDao
            .insert(entityToBeInserted)
            .blockingAwait()

        predictionsDao
            .insert(entityToBeInserted)
            .blockingAwait()

        predictionsDao
            .getPredictions()
            .test()
            .assertValue { it.size == 1 }
    }

    @Test
    fun insertListAndGetPredictions() {
        val entitiesToBeInserted = listOf(
            getTestEntity(),
            getTestEntity("2"),
            getTestEntity("3"),
            getTestEntity("4")
        )

        predictionsDao
            .insert(entitiesToBeInserted)
            .blockingAwait()

        predictionsDao
            .getPredictions()
            .test()
            .assertValue { it.size == 4 }
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        scoreGuesserDB.close()
    }

    private fun getTestEntity(identifier: String = "1") = PredictionRoomEntity(
        matchIdentifier = "matchIdentifier_$identifier",
        homeTeamName = "homeTeamName",
        awayTeamName = "awayTeamName",
        homeScorePrediction = "homeScorePrediction",
        awayScorePrediction = "awayScorePrediction"
    )

    companion object {
        private const val DEFAULT_IDENTIFIER = "matchIdentifier_1"
    }
}
