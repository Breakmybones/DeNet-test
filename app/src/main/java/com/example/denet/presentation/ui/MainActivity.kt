package com.example.denet.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.denet.R
import com.example.denet.presentation.navigation.Navigator

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigator.initialize(navController)
        navigator.attachNavController(navController, R.navigation.main_nav_graph)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.let {
            navigator.detachNavController(it)
        }
    }
}