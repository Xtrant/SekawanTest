package com.example.sekawantest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sekawantest.database.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteUser)

    @Update
    fun updateData(favorite: FavoriteUser)

    @Delete
    fun removeUser(favorite: FavoriteUser)
}