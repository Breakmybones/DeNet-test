package com.example.denet.presentation.navigation

import androidx.navigation.NavController

class Navigator {

    private var navController: NavController? = null

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    fun detachNavController(navController: NavController) {
        if (this.navController == navController) {
            this.navController = null
        }
    }
}