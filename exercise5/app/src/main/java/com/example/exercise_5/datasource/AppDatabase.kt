package com.example.exercise_5.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.exercise_5.ui.parliamentMember.ParliamentMember
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberDao
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfo
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoDao
import com.example.exercise_5.ui.parliamentMemberGrade.ParliamentMemberGrade
import com.example.exercise_5.ui.parliamentMemberGrade.ParliamentMemberGradeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ParliamentMember::class, ParliamentMemberInfo::class, ParliamentMemberGrade::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parliamentMemberDao(): ParliamentMemberDao
    abstract fun parliamentMemberInfoDao(): ParliamentMemberInfoDao
    abstract fun parliamentMemberGradeDao(): ParliamentMemberGradeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "exercise-5-db")
                    .fallbackToDestructiveMigration()           // Wipes and rebuilds instead of migrating if no Migration object.
                    .addCallback(AppDatabaseCallback(scope))    // Migration is not part of this codelab.
                    .build()
                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            println("AppDatabaseCallback onCreate")
            super.onCreate(db)
            // If you want to keep the data through app restarts,
            // comment out the following line.
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.populateDatabase(database.parliamentMemberDao())
                }
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun populateDatabase(parliamentMemberDao: ParliamentMemberDao) {
        println("one time populating the database")
    }

}
