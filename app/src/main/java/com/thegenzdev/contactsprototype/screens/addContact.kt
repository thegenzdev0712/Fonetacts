package com.thegenzdev.contactsprototype.screens

import android.view.WindowInsets
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thegenzdev.contactsprototype.data.ContactItem
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    numberPrefill: String,
    viewModel: ContactViewModel,
    onDone:() -> Unit,
    navController: NavController
){
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {IconButton(onClick = {navController.popBackStack()}) { Icon(Icons.Filled.ArrowBackIosNew, contentDescription = null) }},
                title = { Text("Add Contact")}
            )
        }
    ) { padding ->

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier.background(shape = CircleShape,color = Color.Unspecified)) {
                Icon(Icons.Default.Person, tint = Color.Gray , contentDescription = null, modifier = Modifier.size(300.dp))
            }

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                shape = RoundedCornerShape(15.dp)
            )


            TextField(
                value = numberPrefill,
                onValueChange = {},
                enabled = false,
                label = { Text("Phone Number") },
                shape = RoundedCornerShape(15.dp)
            )


            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    viewModel.addContact(
                        ContactItem(
                            id = 0,
                            name = name,
                            number = numberPrefill,
                            email = email
                        )
                    )
                    onDone()
                    Toast.makeText(context,"Contact saved",Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Save Contact")
            }
        }
    }

}