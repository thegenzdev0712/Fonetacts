package com.thegenzdev.contactsprototype.data

class ContactRepository(
    private val dao: ContactDao
){

    suspend fun getAllContacts() = dao.getAllContacts()

    suspend fun getFavorites() = dao.getFavouriteContacts()

    suspend fun insert(contact: ContactEntity) = dao.insertContact(contact)

    suspend fun update(contact: ContactEntity) = dao.updateContact(contact)

    suspend fun delete(contact: ContactEntity) = dao.deleteContact(contact)

    suspend fun getById(id: Long) = dao.getContactById(id)
}