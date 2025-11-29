package com.thegenzdev.contactsprototype.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ContactDao{

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    suspend fun getAllContacts(): List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE isFavourite = 1 ORDER BY name ASC")
    suspend fun getFavouriteContacts(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Update
    suspend fun updateContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getContactById(id: Long): ContactEntity?

}