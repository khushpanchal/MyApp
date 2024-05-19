package com.example.myapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
                .commit()
        }

    }

    private fun openExtraFragmentFromMainActivity() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ExtraFragment.newInstance(), ExtraFragment.TAG)
            .commit()
    }
}


