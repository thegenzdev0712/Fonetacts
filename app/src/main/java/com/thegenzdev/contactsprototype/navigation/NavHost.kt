package com.thegenzdev.contactsprototype.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thegenzdev.contactsprototype.screens.AddContactScreen
import com.thegenzdev.contactsprototype.screens.ContactDetailsScreen
import com.thegenzdev.contactsprototype.screens.ContactScreen
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModel


sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object AddContact : NavRoutes("add_contact/{number}") {
        fun pass(number: String) = "add_contact/$number"
    }

    object Details : NavRoutes("details/{id}") {
        fun pass(id: Long) = "details/$id"
    }

}


@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: ContactViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            ContactScreen(navController, viewModel)
        }

        composable(NavRoutes.AddContact.route) { backStack ->
            val number = backStack.arguments?.getString("number") ?: ""
            AddContactScreen(
                numberPrefill = number,
                viewModel = viewModel,
                onDone = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(NavRoutes.Details.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLong()

            ContactDetailsScreen(
                id = id!!,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}