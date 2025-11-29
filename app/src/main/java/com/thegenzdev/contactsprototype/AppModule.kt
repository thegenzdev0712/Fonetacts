package com.thegenzdev.contactsprototype

import android.content.Context
import androidx.room.Room
import com.thegenzdev.contactsprototype.data.ContactDatabase
import com.thegenzdev.contactsprototype.data.ContactRepository

object AppModule{

    fun provideDatabase(context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contacts_db"
        ).build()
    }

    fun provideRepository(db: ContactDatabase): ContactRepository {
        return ContactRepository(db.contactDao())
    }
}