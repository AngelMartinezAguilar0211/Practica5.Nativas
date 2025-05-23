package com.example.practica5nativas.ejercicio2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBook::class, SearchHistoryEntity::class], version = 3)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "open_library_db"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
