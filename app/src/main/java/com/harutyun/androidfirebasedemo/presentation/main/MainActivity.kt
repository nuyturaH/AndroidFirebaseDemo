package com.harutyun.androidfirebasedemo.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harutyun.androidfirebasedemo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoLogin()
    }

    private fun autoLogin() {
        val graphInflater = navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_graph)

        val destination =
            if (Firebase.auth.currentUser != null) R.id.listFragment else R.id.signInFragment
        navGraph.setStartDestination(destination)
        navController.graph = navGraph
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}