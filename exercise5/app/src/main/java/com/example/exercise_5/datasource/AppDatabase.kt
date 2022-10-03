package com.example.exercise_5.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.exercise_5.ui.member.Member
import com.example.exercise_5.ui.member.MemberDao
import com.example.exercise_5.ui.memberinfo.MemberInfo
import com.example.exercise_5.ui.memberinfo.MemberInfoDao
import com.example.exercise_5.ui.membergrade.MemberGrade
import com.example.exercise_5.ui.membergrade.MemberGradeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Member::class, MemberInfo::class, MemberGrade::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parliamentMemberDao(): MemberDao
    abstract fun parliamentMemberInfoDao(): MemberInfoDao
    abstract fun parliamentMemberGradeDao(): MemberGradeDao

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
    suspend fun populateDatabase(memberDao: MemberDao) {
        println("one time populating the database")
    }

}
