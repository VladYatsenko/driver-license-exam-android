package com.testdai.core.navigation

import android.os.Bundle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

typealias Navigate = (NavActions.Destination) -> Unit

class NavActions(private val navController: NavHostController) {

    enum class Destination(val route: String) {
        Home("home"),
        Exam("exam"),
        Settings("settings")
    }

    fun navigateByDestination(
        destination: Destination,
        args: Bundle = Bundle(),
        popUpBackStack: Boolean = false
    ) {
        navController.currentBackStackEntry?.savedStateHandle
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            if (popUpBackStack) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = false
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}