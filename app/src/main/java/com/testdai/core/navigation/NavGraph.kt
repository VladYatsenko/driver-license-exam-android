package com.testdai.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testdai.ui.screen.home.HomeScreen

@Composable
fun DriverLicenseExamNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: NavActions.Destination = NavActions.Destination.Home
) {
    val navigationActions = remember(navController) {
        NavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(NavActions.Destination.Home.route) {
            HomeScreen {
                navigationActions.navigateByDestination(it)
            }
        }
//        composable(NavActions.Destination.Exam.route) {
//            val interestsViewModel: InterestsViewModel = viewModel(
//                factory = InterestsViewModel.provideFactory(appContainer.interestsRepository)
//            )
//            InterestsRoute(
//                interestsViewModel = interestsViewModel,
//                isExpandedScreen = isExpandedScreen,
//                openDrawer = openDrawer
//            )
//        }
    }
}