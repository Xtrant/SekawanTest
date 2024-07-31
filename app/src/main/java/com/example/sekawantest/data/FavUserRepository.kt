package com.example.sekawantest.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sekawantest.database.FavoriteUser
import com.example.sekawantest.database.FavoriteUserDao
import com.example.sekawantest.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavoriteDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = FavoriteUserRoomDatabase.getInstance(application)
        mFavoriteDao = database.favoriteUserDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> = mFavoriteDao.getAllUser()

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.removeUser(favorite) }
    }

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
}