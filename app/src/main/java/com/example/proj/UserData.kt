package com.example.proj

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proj.Model.User


@Database(entities = [User::class], version = 3)
abstract class UserData : RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object {
        @Volatile
        private var INSTANCE : UserData? = null
        fun getDatabase(context: Context) : UserData{
            val tempInstance = INSTANCE
            if (tempInstance !=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserData::class.java,
                    "profile_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}