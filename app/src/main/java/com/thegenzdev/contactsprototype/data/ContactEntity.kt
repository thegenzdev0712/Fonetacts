package com.thegenzdev.contactsprototype.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val number: String,
    val email: String? = null,
    val isFavourite: Boolean = false,
)