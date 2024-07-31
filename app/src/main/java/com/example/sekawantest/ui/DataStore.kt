package com.example.sekawantest.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.sekawantest.databinding.ActivityDataStoreBinding

class DataStore : AppCompatActivity() {
    private lateinit var binding: ActivityDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        val isDark = intent.getBooleanExtra("isDark", false)
        binding.switchTheme.isChecked = isDark

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}


