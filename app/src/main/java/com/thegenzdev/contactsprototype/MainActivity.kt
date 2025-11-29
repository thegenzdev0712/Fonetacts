package com.thegenzdev.contactsprototype

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.thegenzdev.contactsprototype.navigation.AppNavHost
import com.thegenzdev.contactsprototype.screens.ContactScreen
import com.thegenzdev.contactsprototype.ui.theme.ContactsPrototypeTheme
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModel
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModelFactory
import androidx.core.graphics.drawable.toDrawable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window,false)

        // remove white flashing when app is navigated
        window.setBackgroundDrawable(Color.Transparent.value.toInt().toDrawable())


        ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CALL_PHONE),1)

        enableEdgeToEdge()
        setContent {
            ContactsPrototypeTheme {

                val navController = rememberNavController()

                val context = LocalContext.current

                val db = remember { AppModule.provideDatabase(context) }
                val repo = remember { AppModule.provideRepository(db) }
                val factory = remember { ContactViewModelFactory(repo) }

                val viewModel: ContactViewModel = viewModel(factory = factory)

                AppNavHost(
                    navController,
                    viewModel
                )
            }
        }
    }

}

