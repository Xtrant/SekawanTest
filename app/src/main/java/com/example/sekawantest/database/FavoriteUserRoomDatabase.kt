package com.example.sekawantest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 8)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserRoomDatabase? = null
        fun getInstance(context: Context): FavoriteUserRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserRoomDatabase::class.java, "Favorite.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}