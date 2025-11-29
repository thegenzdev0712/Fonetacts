package com.thegenzdev.contactsprototype.data

data class ContactItem(
    val id: Long = 0,
    val name: String,
    val number: String,
    val email: String? = null,
    val favourite: Boolean = false
)