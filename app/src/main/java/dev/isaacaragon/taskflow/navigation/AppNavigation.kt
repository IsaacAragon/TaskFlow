package dev.isaacaragon.taskflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = ListScreen)
    {
        composable<ListScreen>
        {
            ListScreen(navController = navController)
        }
        composable<NewTaskScreen>{ backStackEntry ->
            val route = backStackEntry.toRoute<NewTaskScreen>()
            NewTaskScreen(navController = navController,
                taskId = route.taskId)
        }

    }
}