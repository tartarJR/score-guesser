package com.tatar.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tatar.local.room.feature.prediction.dao.PredictionsDao
import com.tatar.local.room.feature.prediction.entity.PredictionRoomEntity

/*
    Increase the version by one and provide the necessary migration anytime the database schema changes, otherwise the app will crash
*/
@Database(
    entities = [PredictionRoomEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ScoreGuesserDB : RoomDatabase() {
    abstract val predictionsDao: PredictionsDao

    companion object {

        private const val DATABASE_NAME = "score_guesser_db"

        @Volatile
        private var instance: ScoreGuesserDB? = null

        fun getInstance(context: Context): ScoreGuesserDB {
            synchronized(this) {
                var smartCastInstance = instance

                if (smartCastInstance == null) {
                    smartCastInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ScoreGuesserDB::class.java,
                        DATABASE_NAME
                    ).build()

                    instance = smartCastInstance
                }

                return smartCastInstance
            }
        }
    }
}
