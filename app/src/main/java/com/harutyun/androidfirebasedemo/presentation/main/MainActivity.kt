package com.harutyun.androidfirebasedemo.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.harutyun.androidfirebasedemo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}