package com.example.sekawantest.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sekawantest.data.FavUserRepository
import com.example.sekawantest.database.FavoriteUser
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val mFavoriteRepository: FavUserRepository = FavUserRepository(application)

    init {
        getAllUsers()
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> {
        return mFavoriteRepository.getAllUsers()
    }


    fun isFavoriteUser(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    private fun addFavUser(fav: FavoriteUser) {
        isFavoriteUser(false)
        mFavoriteRepository.insert(fav)
    }

    private fun removeFavUser(fav: FavoriteUser) {
        isFavoriteUser(true)
        viewModelScope.launch {
            mFavoriteRepository.delete(fav)
            getAllUsers()
        }
    }

    fun updateFavList(fav: FavoriteUser, context: DetailActivity) {
        if (isFavorite.value != true) {
            addFavUser(fav)
            Toast.makeText(context, "Added to Favorite List", Toast.LENGTH_SHORT).show()
        } else {
            removeFavUser(fav)
            Toast.makeText(context, "Removed from Favorite List", Toast.LENGTH_SHORT).show()
        }
    }
}