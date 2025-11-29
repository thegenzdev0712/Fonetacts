package com.thegenzdev.contactsprototype.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thegenzdev.contactsprototype.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.thegenzdev.contactsprototype.data.ContactItem
import com.thegenzdev.contactsprototype.mapper.toContactItem
import com.thegenzdev.contactsprototype.mapper.toEntity

class ContactViewModel(
    private val repository: ContactRepository
): ViewModel(){

    private val _contacts = MutableStateFlow<List<ContactItem>>(emptyList())
    val contacts: StateFlow<List<ContactItem>> = _contacts

    private val _favorites = MutableStateFlow<List<ContactItem>>(emptyList())
    val favorites: StateFlow<List<ContactItem>> = _favorites

    init {
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            _contacts.value = repository.getAllContacts().map { it.toContactItem() }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites().map { it.toContactItem() }
        }
    }

    fun addContact(contact: ContactItem) {
        viewModelScope.launch {
            repository.insert(contact.toEntity())
            loadContacts()
        }
    }

    fun updateContact(contact: ContactItem) {
        viewModelScope.launch {
            repository.update(contact.toEntity())
            loadContacts()
        }
    }

    fun deleteContact(contact: ContactItem) {
        viewModelScope.launch {
            repository.delete(contact.toEntity())
            loadContacts()
        }
    }

    fun getContact(id: Long, onResult: (ContactItem?) -> Unit) {
        viewModelScope.launch {
            val entity = repository.getById(id)
            onResult(entity?.toContactItem())
        }
    }

}