package com.thegenzdev.contactsprototype.screens

import android.R
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.thegenzdev.contactsprototype.data.ContactItem
import com.thegenzdev.contactsprototype.mapper.toEntity
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    id: Long,
    viewModel: ContactViewModel,
    navController: NavController
) {
    // safe container for the loaded contact
    var contact by remember { mutableStateOf<ContactItem?>(null) }

    val context = LocalContext.current

    // load contact once for the given id
    LaunchedEffect(id) {
        viewModel.getContact(id) { item ->
            contact = item
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {IconButton(onClick = { navController.popBackStack() }) { Icon(
                    Icons.Default.ArrowBackIosNew, contentDescription = "back"
                ) }},
                title = { Text("Contact Info") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // If contact is not yet loaded, show a centered loading state
            if (contact == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "You can't see ME!!")
                }
            }


            else {
                val c = contact!!

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(0.2f),
                        modifier = Modifier.size(150.dp)
                    ){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(contact!!.name.first().toString().replaceFirstChar { it.uppercaseChar() }, fontSize = 55.sp)
                        }
                    }

                    Text(text = c.name, style = MaterialTheme.typography.headlineLarge)

                    Text(text = c.number, fontSize = 45.sp)



                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = {
                                val intent = Intent(Intent.ACTION_CALL).apply{
                                    data = Uri.parse("tel:${contact!!.number}")
                                }

                                context.startActivity(intent)
                            }
                                , modifier = Modifier.height(50.dp)) {
                                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(25.dp))
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text("Call", fontSize = 15.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = { }, modifier = Modifier.height(50.dp)) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text("Edit", fontSize = 15.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = {
                                viewModel.deleteContact(contact!!)
                                navController.popBackStack()
                                Toast.makeText(context,"Contact deleted successfully",Toast.LENGTH_SHORT).show()
                            }, modifier = Modifier.height(50.dp)) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text("Delete", fontSize = 15.sp)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = {

                            }, modifier = Modifier.height(50.dp)) {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text("Favourite", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                        }
                    }


                }

                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    Text("Additional Info", style = MaterialTheme.typography.bodyLarge)

                    if(contact!!.email != "") {
                        Text("✉️ ${contact!!.email}")
                    }

                    else{
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                            Icon(Icons.Default.Inbox, contentDescription = null, modifier = Modifier.size(60.dp))
                            Text("Nothing here yet...")
                        }
                    }
                }

            }
        }
    }
}