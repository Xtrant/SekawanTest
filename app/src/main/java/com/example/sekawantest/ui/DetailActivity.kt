package com.example.sekawantest.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.sekawantest.R
import com.example.sekawantest.data.response.DetailUserResponse
import com.example.sekawantest.database.FavoriteUser
import com.example.sekawantest.databinding.DetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: DetailUserBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val avatarUrl = intent.getStringExtra(AVATAR_URL)

        //Live Data
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        if (username != null) {
            mainViewModel.detailUser(username)
        }
        mainViewModel.detailUserData.observe(this) { detailUser ->
            setDetailUserData(detailUser)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //tab layout mediator
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username
        }
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        //share button
        colorSetting()

        binding.btnShare.setOnClickListener {
            createSharedIntent("Check this User Detail")
        }

        //favorite button
        var favorite: FavoriteUser = FavoriteUser().apply {
            if (username != null) {
                this.login = username
                this.avatarUrl = avatarUrl
            }
        }

        favoriteViewModel.isFavorite.observe(this) {
            isFav(it)
        }

        favoriteViewModel.getAllUsers().observe(this) { user ->
            user?.forEach { users ->
                if (users.login == username) {
                    favoriteViewModel.isFavoriteUser(true)
                    favorite = users
                }
            }
        }

        binding.fbFav.setOnClickListener {
            favorite.let { fav ->
                favoriteViewModel.updateFavList(fav, this)
            }
        }
    }

    //function
    private fun setDetailUserData(detailUser: DetailUserResponse) {
        binding.tvName.text = detailUser.name
        binding.tvUsername.text = detailUser.login
        binding.amountFollower.text = detailUser.followers.toString()
        binding.amountFollowing.text = detailUser.following.toString()
        Glide.with(this@DetailActivity)
            .load(detailUser.avatarUrl)
            .into(binding.imgGameDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun createSharedIntent(body: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(shareIntent, "Share with"))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun isFav(isFavorited: Boolean) {
        if (isFavorited) {
            binding.fbFav.setImageDrawable(getDrawable(R.drawable.fav_on))
        } else {
            binding.fbFav.setImageDrawable(getDrawable(R.drawable.fav_off))
        }
    }

    private fun colorSetting() {
        val isNightMode = resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        if (isNightMode) {
            binding.btnShare.setColorFilter(ContextCompat.getColor(this, R.color.white))
        } else {
            binding.btnShare.setColorFilter(ContextCompat.getColor(this, R.color.black))
        }
    }


    companion object {
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}