package com.example.sekawantest.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sekawantest.R
import com.example.sekawantest.data.response.ItemsItem
import com.example.sekawantest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initiate main view model class
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, i, _ ->
                    searchBar.hint = searchView.text
                    searchView.hide()
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        val q = searchView.text.toString()
                        mainViewModel.showUser(q)
                    }
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.userData.observe(this) { user ->
            setUserData(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.btnListFav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        colorSetting()



        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun setUserData(user: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun colorSetting() {
        val isNightMode = resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        if (isNightMode) {
            binding.btnSetting.setColorFilter(ContextCompat.getColor(this, R.color.white))
        } else {
            binding.btnSetting.setColorFilter(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
