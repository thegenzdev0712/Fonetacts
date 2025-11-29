package com.thegenzdev.contactsprototype.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thegenzdev.contactsprototype.data.ContactItem
import com.thegenzdev.contactsprototype.navigation.NavRoutes
import com.thegenzdev.contactsprototype.viewmodel.ContactViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController, viewModel: ContactViewModel){

    val context = LocalContext.current

    val contacts by viewModel.contacts.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDialpad by remember { mutableStateOf(false) }
    var typedNumber by remember { mutableStateOf("") }


    Scaffold(
        topBar = { TopAppBar(
            navigationIcon = {IconButton(onClick = {}) { Icon(Icons.Filled.Menu, contentDescription = null) }},
            title = { Text("Fonetacts", modifier = Modifier.padding(15.dp,0.dp,15.dp,0.dp)) },
            actions = {
                IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = null)}
            },
        )},
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialpad = true }, modifier = Modifier.size(65.dp)) {
                Icon(Icons.Default.Dialpad, contentDescription = null)
            }
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { padding->

        Column(modifier =
            Modifier.fillMaxSize().padding(padding)
        ){

            if(showDialpad){
                ModalBottomSheet(
                    onDismissRequest = { showDialpad = false },
                    sheetState = sheetState,
                    dragHandle = null
                ) {
                    DialpadSheet(
                        typedNumber = typedNumber,
                        onNumberChange = { newValue ->
                            typedNumber = newValue },
                        onCall = {
                            if(typedNumber.isNotEmpty()){
                                val intent = Intent(Intent.ACTION_CALL).apply{
                                    data = Uri.parse("tel:$typedNumber")
                                }

                                context.startActivity(intent)
                            }
                        },
                        onSave = {
                            showDialpad = false
                            navController.navigate(NavRoutes.AddContact.pass(typedNumber))
                        }
                    )
                }
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                // the category row is an item for the lazyColumn
               item {
                   Row(
                       modifier = Modifier.fillMaxWidth()
                           .padding(8.dp)
                           .horizontalScroll(rememberScrollState()),
                       horizontalArrangement = Arrangement.spacedBy(12.dp)
                   ) {
                       Button(
                           onClick = {},
                           shape = RoundedCornerShape(15.dp),
                           modifier = Modifier.padding(1.dp)
                       ) { Text("All") }
                       Button(
                           onClick = {},
                           shape = RoundedCornerShape(15.dp),
                           modifier = Modifier.padding(1.dp)
                       ) { Text("Favourites") }
                       Button(
                           onClick = {},
                           shape = RoundedCornerShape(15.dp),
                           modifier = Modifier.padding(1.dp)
                       ) { Text("Business") }
                       Button(
                           onClick = {},
                           shape = RoundedCornerShape(15.dp),
                           modifier = Modifier.padding(1.dp)
                       ) { Text("Professional") }
                   }
               }

                items(contacts){ contact ->
                    ContactRow(contact,navController)
                    HorizontalDivider()
                }
            }
        }
    }
}


@Composable
fun ContactRow(contact: ContactItem, navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .padding(horizontal = 4.dp)
            .clickable(onClick= {
                    navController.navigate(NavRoutes.Details.pass(contact.id))
            } ,enabled = true, interactionSource = remember { MutableInteractionSource() }, indication = null),
        verticalAlignment = Alignment.CenterVertically
    ){
        val context = LocalContext.current

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary.copy(0.2f),
            modifier = Modifier.size(42.dp)
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text(contact.name.first().toString().replaceFirstChar { it.uppercaseChar() })
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, style = MaterialTheme.typography.titleMedium)
            Text(contact.number, style = MaterialTheme.typography.bodyMedium)
        }

        IconButton(onClick = {
            if(contact.number.isNotEmpty()){
                val intent = Intent(Intent.ACTION_CALL).apply{
                    data = Uri.parse("tel:${contact.number}")
                }

                context.startActivity(intent)
            }
        }) {
            Icon(Icons.Default.Call,contentDescription = null)
        }
    }
}


@Composable
fun DialpadSheet(
    typedNumber: String,
    onNumberChange: (String) -> Unit,
    onCall: () -> Unit,
    onSave: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = typedNumber,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp).weight(1f)
            )

            IconButton(onClick = {
                if (typedNumber.isNotEmpty()) {
                    onNumberChange(typedNumber.dropLast(1))
                }
            }) { Icon(Icons.AutoMirrored.Filled.Backspace,contentDescription = null) }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RowDial("1", "O_O",   "2", "ABC",   "3", "DEF") { onNumberChange(typedNumber + it) }
            RowDial("4", "GHI",  "5", "JKL",   "6", "MNO") { onNumberChange(typedNumber + it) }
            RowDial("7", "PQRS", "8", "TUV",   "9", "WXYZ") { onNumberChange(typedNumber + it) }
            RowDial("*", null,   "0", "+",     "#", null) { onNumberChange(typedNumber + it) }

        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCall, modifier = Modifier.height(60.dp) ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Call")
            }
            Button(onClick = onSave, modifier = Modifier.height(60.dp) ) {
                Icon(
                    imageVector = Icons.Default.PersonAddAlt,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Save")
            }
        }
    }
}



@Composable
fun RowDial(
    a: String, aLetters: String?,
    b: String, bLetters: String?,
    c: String, cLetters: String?,
    onPress: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DialButton(a, aLetters) { onPress(a) }
        DialButton(b, bLetters) { onPress(b) }
        DialButton(c, cLetters) { onPress(c) }
    }
}

@Composable
fun DialButton(label: String, letters: String? = null, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current

    Surface(
        shape = RoundedCornerShape(25.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 3.dp,
        modifier = Modifier
            .size(85.dp)
            .padding(4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp
            )

            if (letters != null) {
                Text(
                    text = letters,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
