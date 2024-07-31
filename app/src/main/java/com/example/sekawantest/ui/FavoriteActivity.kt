package com.example.sekawantest.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sekawantest.data.response.ItemsItem
import com.example.sekawantest.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.listFav.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listFav.addItemDecoration(itemDecoration)
    }

    override fun onResume() {
        super.onResume()

        val favoriteViewModel by viewModels<FavoriteViewModel> {
            FavoriteViewModelFactory.getInstance(application)
        }
        favoriteViewModel.getAllUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }

            if (items.isNotEmpty()) {
                val adapter = UserAdapter()
                adapter.submitList(items)
                binding.listFav.adapter = adapter
            } else binding.tvEmpty.visibility = View.VISIBLE
        }
    }

}
