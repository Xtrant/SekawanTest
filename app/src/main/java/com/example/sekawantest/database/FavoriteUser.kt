package com.example.sekawantest.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "login")
    var login: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null
) : Parcelable
