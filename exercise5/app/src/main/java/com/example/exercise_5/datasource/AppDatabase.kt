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
import com.example.exercise_5.ui.party.Party
import com.example.exercise_5.ui.party.PartyDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
@Database(entities = [Member::class, MemberInfo::class, MemberGrade::class, Party::class], version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun memberInfoDao(): MemberInfoDao
    abstract fun memberGradeDao(): MemberGradeDao
    abstract fun partyDao(): PartyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "exercise-5-db")
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
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

            // If you want to keep the data through app restarts, comment out the following line.
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.populateDatabase(database.memberDao())
                }
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun populateDatabase(memberDao: MemberDao) {
        println("one time populating the database")
    }

}
